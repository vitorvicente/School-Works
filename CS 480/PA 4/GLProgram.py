"""
    Main GLSL Program File.

    :author: micou(Zezhou Sun), vitor@bu.edu.
    :version: 2021.12.4.
"""

from Light import Light

try:
    import OpenGL

    try:
        import OpenGL.GL as gl
        import OpenGL.GLU as glu
    except ImportError:
        from ctypes import util

        orig_util_find_library = util.find_library


        def new_util_find_library(name):
            res = orig_util_find_library(name)
            if res:
                return res
            return '/System/Library/Frameworks/' + name + '.framework/' + name


        util.find_library = new_util_find_library
        import OpenGL.GL as gl
        import OpenGL.GLU as glu
except ImportError:
    raise ImportError("Required dependency PyOpenGL not present")
import numpy as np
import math


def perspectiveMatrix(angleOfView, near, far):
    result = np.identity(4)
    angleOfView = min(179, max(0, angleOfView))
    scale = 1 / math.tan(0.5 * angleOfView * math.pi / 180)
    fsn = far - near
    result[0, 0] = scale
    result[1, 1] = scale
    result[2, 2] = - far / fsn
    result[3, 2] = - far * near / fsn
    result[2, 3] = -1
    result[3, 3] = 0


class GLProgram:
    program = None

    vertexShaderSource = None
    fragmentShaderSource = None
    attribs = None

    vs = None
    fs = None

    ready = False
    debug = 0

    def __init__(self) -> None:
        """
            Helper Method to init the Object.

            :return: None.
        """
        self.program = gl.glCreateProgram()

        self.ready = False

        # define attribs name and corresponding method to set it
        self.attribs = {
            "vertexPos": "aPos",
            "vertexNormal": "aNormal",
            "vertexColor": "aColor",
            "vertexTexture": "aTexture",

            "textureImage": "theTexture01",

            "projectionMat": "projection",
            "viewMat": "view",
            "modelMat": "model",

            "viewPosition": "viewPosition",
            "material": "material",
            "light": "light",

            "maxLightsNum": "20",
            "maxMaterialNum": "20"
        }
        self.attribs["diffuse"] = self.attribs["material"] + ".diffuse"
        self.attribs["specular"] = self.attribs["material"] + ".specular"
        self.attribs["ambient"] = self.attribs["material"] + ".ambient"
        self.attribs["highlight"] = self.attribs["material"] + ".highlight"
        for i in range(int(self.attribs["maxLightsNum"])):
            self.attribs[f"light[{i}].position"] = f"{self.attribs['light']}[{i}].position"
            self.attribs[f"light[{i}].color"] = f"{self.attribs['light']}[{i}].color"
            self.attribs[f"light[{i}].infiniteOn"] = f"{self.attribs['light']}[{i}].infiniteOn"
            self.attribs[f"light[{i}].infiniteDirection"] = f"{self.attribs['light']}[{i}].infiniteDirection"
            self.attribs[f"light[{i}].radialOn"] = f"{self.attribs['light']}[{i}].radialOn"
            self.attribs[f"light[{i}].spotRadialFactor"] = f"{self.attribs['light']}[{i}].spotRadialFactor"
            self.attribs[f"light[{i}].spotOn"] = f"{self.attribs['light']}[{i}].spotOn"
            self.attribs[f"light[{i}].spotDirection"] = f"{self.attribs['light']}[{i}].spotDirection"
            self.attribs[f"light[{i}].spotAngleLimit"] = f"{self.attribs['light']}[{i}].spotAngleLimit"

        self.vertexShaderSource = self.genVertexShaderSource()
        self.fragmentShaderSource = self.genFragShaderSource()

    def __del__(self) -> None:
        """
            Helper Method to delete this Object.

            :return: None.
        """
        try:
            gl.glDeleteProgram(self.program)
        except Exception as e:
            pass

    @staticmethod
    def load_shader(src: str, shader_type: int) -> int:
        """
            Helper Method to Load the Shader Source Code.

            :return: shader.
        """
        shader = gl.glCreateShader(shader_type)
        gl.glShaderSource(shader, src)
        gl.glCompileShader(shader)
        error = gl.glGetShaderiv(shader, gl.GL_COMPILE_STATUS)
        if error != gl.GL_TRUE:
            info = gl.glGetShaderInfoLog(shader)
            gl.glDeleteShader(shader)
            raise Exception(info)
        return shader

    def genVertexShaderSource(self):
        """
            Helper Method to generate the Vertex Shader Source Code.

            :return: None.
        """
        vss = f'''
        #version 330 core
        in vec3 {self.attribs["vertexPos"]};
        in vec3 {self.attribs["vertexNormal"]};
        in vec3 {self.attribs["vertexColor"]};
        in vec2 {self.attribs["vertexTexture"]};
        
        out vec3 vPos;
        out vec3 vColor;
        smooth out vec3 vNormal;
        out vec2 vTexture;
        out int materialIndex;
        
        uniform mat4 {self.attribs["projectionMat"]};
        uniform mat4 {self.attribs["viewMat"]};
        uniform mat4 {self.attribs["modelMat"]};
        
        void main()
        {{
            gl_Position = {self.attribs["projectionMat"]} * {self.attribs["viewMat"]} * {self.attribs["modelMat"]} * vec4({self.attribs["vertexPos"]}, 1.0);
            vPos = vec3(model * vec4({self.attribs["vertexPos"]}, 1.0));
            vColor = {self.attribs["vertexColor"]};
            vNormal = normalize(transpose(inverse({self.attribs["modelMat"]})) * vec4({self.attribs["vertexNormal"]}, 0.0) ).xyz;
            vTexture = {self.attribs["vertexTexture"]};
        }}
        '''
        return vss

    def genFragShaderSource(self):
        """
            Helper Method to generate the Fragment Shader Source Code.

            :return: None.
        """
        fss = f"""
        #version 330 core
        #define MAX_LIGHT_NUM {self.attribs["maxLightsNum"]}
        #define MAX_MATERIAL_NUM {self.attribs["maxMaterialNum"]}
        struct Material{{
            vec4 ambient;
            vec4 diffuse;
            vec4 specular;
            float highlight;
        }};
        
        struct Light{{
            vec3 position;
            vec4 color;
            
            bool infiniteOn;
            vec3 infiniteDirection;
            
            bool radialOn;
            
            bool spotOn;
            vec3 spotDirection;
            vec3 spotRadialFactor;
            float spotAngleLimit;
            
            bool ambientOn;
            bool specularOn;
            bool diffuseOn;
        }};
        
        in vec3 vPos;
        in vec3 vColor;
        smooth in vec3 vNormal;
        in vec2 vTexture;
        
        uniform int renderingFlag;
        uniform sampler2D {self.attribs["textureImage"]};
        
        uniform vec3 {self.attribs["viewPosition"]};
        uniform Material {self.attribs["material"]};
        uniform Light {self.attribs["light"]}[MAX_LIGHT_NUM];
        
        out vec4 FragColor;
        void main()
        {{
            // These three lines are meaningless, they only works as attributes placeholder! 
            // Otherwise glsl will optimize out our attributes
            vec4 placeHolder = vec4(vPos+vColor+vNormal+vec3(vTexture, 1), 0);
            FragColor = -1 * abs(placeHolder);
            FragColor = clamp(FragColor, 0, 1);
            
            vec4 results[8];
            for(int i=0; i<8; i+=1)
                results[i]=vec4(0.0);
            int ri=0;
            
            ////////// BONUS 7: Normal Mapping
            // Requirements:
            //   1. Perform the same steps as Texture Mapping above, except that instead of using the image for vertex 
            //   color, the image is used to modify the normals.
            //   2. Use the input normal map (“./assets/normalmap.jpg”) on both the sphere and the torus.

            
            // Reserved for illumination rendering, routing name is "lighting" or "illumination"
            if ((renderingFlag >> 0 & 0x1) == 1){{
                // Declare Result Message
                vec4 result = vec4(0, 0, 0, 1.0);
                
                for (int i = 0; i < MAX_LIGHT_NUM; i++) {{
                    // Render Light for Infinite Lights
                    if ({self.attribs["light"]}[i].radialOn) {{
                        // Ambient Light Final
                        vec4 ambient_light = {self.attribs["material"]}.ambient*{self.attribs["light"]}[i].color;
                        
                        // Diffuse Light Math & Final
                        vec3 light_vector = normalize({self.attribs["light"]}[i].infiniteDirection);
                        float dot_product = dot(light_vector, vNormal);
                        vec4 diffuse_light = {self.attribs["material"]}.diffuse*{self.attribs["light"]}[i].color*dot_product;
                        
                        // Specular Light Math
                        vec3 r_vector = normalize(reflect(normalize(light_vector), normalize(vNormal)));
                        vec3 view_vector = normalize({self.attribs["viewPosition"]} - vPos);
                        float dot_product_two = pow(abs(dot(r_vector, view_vector)),
                         {self.attribs["material"]}.highlight);
                        float distance = length(light_vector);
                        float radial_attenuation = 1;
                        float angular_attenuation = 1;
                        
                        // Specular Light Final
                        vec4 specular_light = {self.attribs["material"]}.specular*{self.attribs["light"]}[i].color*dot_product_two;
                        
                        // If Ambient is Enabled, add it
                        if ({self.attribs["light"]}[i].ambientOn) {{
                            result += ambient_light;
                        }}
                        // If Diffuse is Enabled, add it
                        if ({self.attribs["light"]}[i].diffuseOn) {{
                            result += radial_attenuation * angular_attenuation * diffuse_light;
                        }}
                        // If Specular is Enabled, add it
                        if ({self.attribs["light"]}[i].specularOn) {{
                            result += radial_attenuation * angular_attenuation * specular_light;
                        }}
                    // Render Light for Spotlight Lights
                    }} else if ({self.attribs["light"]}[i].spotOn) {{ 
                        // Ambient Light Final
                        vec4 ambient_light = {self.attribs["material"]}.ambient*{self.attribs["light"]}[i].color;
                        
                        // Diffuse Light Math & Final
                        vec3 light_vector = normalize({self.attribs["light"]}[i].position - vPos);
                        float dot_product = dot(light_vector, vNormal);
                        vec4 diffuse_light = {self.attribs["material"]}.diffuse*{self.attribs["light"]}[i].color*dot_product;
                        
                        // Specular Light Math
                        vec3 r_vector = normalize(reflect(normalize(light_vector), normalize(vNormal)));
                        vec3 view_vector = normalize({self.attribs["viewPosition"]} - vPos);
                        float dot_product_two = pow(abs(dot(r_vector, view_vector)),
                         {self.attribs["material"]}.highlight);
                        float distance = length(light_vector);
                        float radial_attenuation = abs(1 / pow(distance,2));
                        float angular_attenuation = pow(cos({self.attribs["light"]}[i].spotAngleLimit), 0.0001);
                        
                        // Specular Light Final
                        vec4 specular_light = {self.attribs["material"]}.specular*{self.attribs["light"]}[i].color*dot_product_two;
                        
                        // If Ambient is Enabled, add it
                        if ({self.attribs["light"]}[i].ambientOn) {{
                            result += ambient_light;
                        }}
                        // If Diffuse is Enabled, add it
                        if ({self.attribs["light"]}[i].diffuseOn) {{
                            result += radial_attenuation * angular_attenuation * diffuse_light;
                        }}
                        // If Specular is Enabled, add it
                        if ({self.attribs["light"]}[i].specularOn) {{
                            result += radial_attenuation * angular_attenuation * specular_light;
                        }}
                    // Render Light for Point Lights
                    }} else {{
                        // Ambient Light Final
                        vec4 ambient_light = {self.attribs["material"]}.ambient*{self.attribs["light"]}[i].color;
                        
                        // Diffuse Light Math & Final
                        vec3 light_vector = normalize({self.attribs["light"]}[i].position - vPos);
                        float dot_product = dot(light_vector, vNormal);
                        vec4 diffuse_light = {self.attribs["material"]}.diffuse*{self.attribs["light"]}[i].color*dot_product;
                        
                        // Specular Light Math
                        vec3 r_vector = normalize(reflect(normalize(light_vector), normalize(vNormal)));
                        vec3 view_vector = normalize({self.attribs["viewPosition"]} - vPos);
                        float dot_product_two = pow(abs(dot(r_vector, view_vector)),
                         {self.attribs["material"]}.highlight);
                        float distance = length(light_vector);
                        float radial_attenuation = abs(1 / pow(distance,2));
                        float angular_attenuation = 1;
                        
                        // Specular Light Final
                        vec4 specular_light = {self.attribs["material"]}.specular*{self.attribs["light"]}[i].color*dot_product_two;
                        
                        // If Ambient is Enabled, add it
                        if ({self.attribs["light"]}[i].ambientOn) {{
                            result += ambient_light;
                        }}
                        // If Diffuse is Enabled, add it
                        if ({self.attribs["light"]}[i].diffuseOn) {{
                            result += radial_attenuation * angular_attenuation * diffuse_light;
                        }}
                        // If Specular is Enabled, add it
                        if ({self.attribs["light"]}[i].specularOn) {{
                            result += radial_attenuation * angular_attenuation * specular_light;
                        }}
                    }}
                }}
                
                results[ri] = clamp(result, 0, 1);
                ri+=1;
            }}
            
            // Reserved for rendering with vertex color, routing name is "vertex"
            if ((renderingFlag >> 1 & 0x1) == 1){{
                results[ri] = vec4(vColor, 1.0);
                ri+=1;
            }}
            
            // Reserved for rendering with fixed color, routing name is "pure"
            if ((renderingFlag >> 2 & 0x1) == 1){{
                results[ri] = vec4(0.5, 0.5, 0.5, 1.0);
                ri+=1;
            }}
            
            // Reserved for normal rendering, routing name is "normal"
            if ((renderingFlag >> 3 & 0x1) == 1){{
                vec3 base = vec3(0);
                for (int i = 0; i < 3; i++) {{
                    base[i] = abs(vNormal[i]) / 2 + 0.5;
                }}
                
                results[ri] = vec4(normalize(base), 1.0);
                ri+=1;
            }}
            
            // Reserved for artist rendering, routing name is "artist"
            if ((renderingFlag >> 5 & 0x1) == 1){{
            
                ////////// BONUS 8: Artist Rendering (advanced)
                // Requirements:
                //   Look at Section 10.3, “Artistic Shading” in Shirley/Marschner (4th ed.).
                //        Implement line drawing in shader code
                //        Implement cool-to-warm shader code

                results[ri] = vec4(0.5, 0.5, 0.5, 1.0);
                ri+=1;
            }}
            
            // Reserved for some customized rendering, routing name is "custom"
            if ((renderingFlag >> 6 & 0x1) == 1){{
                results[ri] = vec4(0.5, 0.5, 0.5, 1.0);
                ri+=1;
            }}
            
            // Reserved for texture mapping, get point color from texture image and texture coordinates
            // Routing name is "texture"
            if ((renderingFlag >> 8 & 0x1) == 1){{
                results[ri] = texture({self.attribs["textureImage"]}, vTexture);
                ri+=1;
            }}
            
            // Mix all result in results array
            vec4 outputResult=vec4(0.0);
            for(int i=0; i<ri; i++){{
                outputResult += results[i]/ri;
            }}
            FragColor = outputResult;
        }}
        """
        return fss

    def set_vss(self, vss: str):
        """
            Helper Method to set the Vertex Shader Source Code.

            :return: None.
        """
        if not isinstance(vss, str):
            raise TypeError("Vertex shader source code must be a string")
        self.vertexShaderSource = vss

    def set_fss(self, fss):
        """
            Helper Method to set the Fragment Shader Source Code.

            :return: None.
        """
        if not isinstance(fss, str):
            raise TypeError("Fragment shader source code must be a string")
        self.fragmentShaderSource = fss

    def getAttribLocation(self, name):
        """
            Helper Method to get a Attribute Location from GL.

            :return: None.
        """
        programName = self.getAttribName(name)
        attribLoc = gl.glGetAttribLocation(self.program, programName)
        if attribLoc == -1 and self.debug > 1:
            print(f"Warning: Attrib {name} cannot found. Might have been optimized off")
        return attribLoc

    def getUniformLocation(self, name, lookThroughAttribs=True):
        """
            Helper Method to get a Uniform value from GL.

            :return: None.
        """
        if lookThroughAttribs:
            variableName = self.getAttribName(name)
        else:
            variableName = name
        uniformLoc = gl.glGetUniformLocation(self.program, variableName)
        if uniformLoc == -1 and self.debug > 1:
            print(f"Warning: Uniform {name} cannot found. Might have been optimized off")
        return uniformLoc

    def getAttribName(self, attribIndexName):
        return self.attribs[attribIndexName]

    def compile(self, vs_src=None, fs_src=None) -> None:
        """
            Method to Compile the GL Program.

            :return: None.
        """
        if vs_src:
            self.set_vss(vs_src)
        else:
            vs_src = self.vertexShaderSource

        if fs_src:
            self.set_fss(fs_src)
        else:
            fs_src = self.fragmentShaderSource

        if not (vs_src and fs_src):
            raise Exception("shader source code missing")

        vs = self.load_shader(vs_src, gl.GL_VERTEX_SHADER)
        if not vs:
            return
        fs = self.load_shader(fs_src, gl.GL_FRAGMENT_SHADER)
        if not fs:
            return
        gl.glAttachShader(self.program, vs)
        gl.glAttachShader(self.program, fs)
        gl.glLinkProgram(self.program)
        error = gl.glGetProgramiv(self.program, gl.GL_LINK_STATUS)
        if error != gl.GL_TRUE:
            info = gl.glGetShaderInfoLog(self.program)
            raise Exception(info)

        self.ready = True

    def setFragmentShaderRouting(self, routing="lighting"):
        """
            There will be different rendering routing:
            "lighting"/"illumination": DEFAULT routing. Rendering the scene with lights.
            "vertex": use VBO stored vertex color to render object.
            "pure": render object with pre-defined color.
            "normal": render with vertex's normal.
            "bump": normal mapping.
            "artist": artist rendering.
            "custom": some customized rendering.
            "texture": this must use previous routing, if set to true, then mix color with texture.

            :return: None.
        """
        renderingFlag = 0
        if isinstance(routing, str):
            routing = routing.lower()
            if ("lighting" in routing) or ("illumination" in routing):
                renderingFlag = renderingFlag | 0x1
            if "vertex" in routing:
                renderingFlag = renderingFlag | (0x1 << 1)
            if "pure" in routing:
                renderingFlag = renderingFlag | (0x1 << 2)
            if "normal" in routing:
                renderingFlag = renderingFlag | (0x1 << 3)
            if "bump" in routing:
                renderingFlag = renderingFlag | (0x1 << 4)
            if "artist" in routing:
                renderingFlag = renderingFlag | (0x1 << 5)
            if "custom" in routing:
                renderingFlag = renderingFlag | (0x1 << 6)
            if "texture" in routing:
                renderingFlag = renderingFlag | (0x1 << 8)

        self.use()
        self.setInt("renderingFlag", renderingFlag, lookThroughAttribs=False)

    def use(self):
        """
            This is required before the uniforms set up.

            :return: None.
        """
        if not self.ready:
            raise Exception("GLProgram must compile before use it")
        gl.glUseProgram(self.program)

    def setLight(self, lightIndex: int, light: Light):
        """
            Helper Method to Set a Light Object. Method Updated to include all Light Properties.

            :return: None.
        """
        if not isinstance(light, Light):
            raise TypeError("light type must be Light")

        self.setVec3(f"""{self.attribs["light"]}[{lightIndex}].position""", light.position, False)
        self.setVec4(f"""{self.attribs["light"]}[{lightIndex}].color""", light.color, False)
        self.setBool(f"""{self.attribs["light"]}[{lightIndex}].infiniteOn""", light.infiniteOn, False)
        self.setVec3(f"""{self.attribs["light"]}[{lightIndex}].infiniteDirection""", light.infiniteDirection, False)
        self.setBool(f"""{self.attribs["light"]}[{lightIndex}].radialOn""", light.radialOn, False)
        self.setBool(f"""{self.attribs["light"]}[{lightIndex}].spotOn""", light.spotOn, False)
        self.setVec3(f"""{self.attribs["light"]}[{lightIndex}].spotDirection""", light.spotDirection, False)
        self.setVec3(f"""{self.attribs["light"]}[{lightIndex}].spotRadialFactor""", light.spotRadialFactor, False)
        self.setFloat(f"""{self.attribs["light"]}[{lightIndex}].spotAngleLimit""", light.spotAngleLimit, False)
        self.setBool(f"""{self.attribs["light"]}[{lightIndex}].ambientOn""", light.ambientOn, False)
        self.setBool(f"""{self.attribs["light"]}[{lightIndex}].diffuseOn""", light.diffuseOn, False)
        self.setBool(f"""{self.attribs["light"]}[{lightIndex}].specularOn""", light.specularOn, False)



    def clearAllLights(self):
        """
            Helper Method to Clear all current Lights.
        """
        maxLightsNum = int(self.attribs["maxLightsNum"])
        light = Light()
        for i in range(maxLightsNum):
            self.setLight(i, light)

    def setMat4(self, name, mat, lookThroughAttribs=True):
        """
            Helper Method to Set a 4x4 Matrix.

            :return: None.
        """
        self.use()
        if mat.shape != (4, 4):
            raise Exception("Projection Matrix must have 4x4 shape")
        gl.glUniformMatrix4fv(self.getUniformLocation(name, lookThroughAttribs), 1, gl.GL_FALSE, mat.flatten("C"))

    def setMat3(self, name, mat, lookThroughAttribs=True):
        """
            Helper Method to Set a 3x3 Matrix.

            :return: None.
        """
        self.use()
        if mat.shape != (3, 3):
            raise Exception("Projection Matrix must have 3x3 shape")
        gl.glUniformMatrix3fv(self.getUniformLocation(name, lookThroughAttribs), 1, gl.GL_FALSE, mat.flatten("C"))

    def setMat2(self, name, mat, lookThroughAttribs=True):
        """
            Helper Method to Set a 2x2 Matrix.

            :return: None.
        """
        self.use()
        if mat.shape != (2, 2):
            raise Exception("Projection Matrix must have 2x2 shape")
        gl.glUniformMatrix2fv(self.getUniformLocation(name, lookThroughAttribs), 1, gl.GL_FALSE, mat.flatten("C"))

    def setVec4(self, name, vec, lookThroughAttribs=True):
        """
            Helper Method to Set a Vector of Length 4.

            :return: None.
        """
        self.use()
        if vec.size != 4:
            raise Exception("Vector must have size 4")
        gl.glUniform4fv(self.getUniformLocation(name, lookThroughAttribs), 1, vec)

    def setVec3(self, name, vec, lookThroughAttribs=True):
        """
            Helper Method to Set a Vector of Length 3.

            :return: None.
        """
        self.use()
        if vec.size != 3:
            raise Exception("Vector must have size 3")
        gl.glUniform3fv(self.getUniformLocation(name, lookThroughAttribs), 1, vec)

    def setVec2(self, name, vec, lookThroughAttribs=True):
        """
            Helper Method to Set a Vector of Length 2.

            :return: None.
        """
        self.use()
        if vec.size != 2:
            raise Exception("Vector must have size 2")
        gl.glUniform2fv(self.getUniformLocation(name, lookThroughAttribs), 1, vec)

    def setBool(self, name, value, lookThroughAttribs=True):
        """
            Helper Method to Set a Boolean.

            :return: None.
        """
        self.use()
        if value not in (0, 1):
            raise Exception("bool only accept True/False/0/1")
        gl.glUniform1i(self.getUniformLocation(name, lookThroughAttribs), int(value))

    def setInt(self, name, value, lookThroughAttribs=True):
        """
            Helper Method to Set an Integer.

            :return: None.
        """
        self.use()
        if value != int(value):
            raise Exception("set int only accept  integer")
        gl.glUniform1i(self.getUniformLocation(name, lookThroughAttribs), int(value))

    def setFloat(self, name, value, lookThroughAttribs=True):
        """
            Helper Method to Set a Float.

            :return: None.
        """
        self.use()
        gl.glUniform1f(self.getUniformLocation(name, lookThroughAttribs), float(value))
