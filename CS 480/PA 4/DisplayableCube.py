"""
    Displayable Cube Class. Sets up the Cube Vertices, Normals, Color, and EBO Indexes.

    :author: vitor@bu.edu.
    :version: 2021.12.4.
"""

from Displayable import Displayable
from GLBuffer import VAO, VBO, EBO
import numpy as np
import ColorType

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


class DisplayableCube(Displayable):
    vao = None
    vbo = None
    ebo = None
    shaderProg = None

    vertices = None
    indices = None

    length = None
    width = None
    height = None
    color = None

    def __init__(self, shaderProg, length=1, width=1, height=1, color=ColorType.BLUE):
        """
            Init the whole Object, calling Helper Methods to build the Structure.
        """
        super(DisplayableCube, self).__init__()
        self.shaderProg = shaderProg
        self.shaderProg.use()

        self.vao = VAO()
        self.vbo = VBO()
        self.ebo = EBO()

        self.generate(length, width, height, color)

    def generate(self, length=1, width=1, height=1, color=ColorType.BLUE):
        """
            Generate the Cube Structure, using a Vertex and Index List.

            :return: None.
        """
        self.length = length
        self.width = width
        self.height = height
        self.color = color

        self.vertices = np.zeros([24, 11])

        vl = np.array([
            -length / 2, width / 2, height / 2, 0, 0, 1, *color,
            -length / 2, width / 2, height / 2, -1, 0, 0, *color,
            -length / 2, width / 2, height / 2, 0, 1, 0, *color,
            -length / 2, width / 2, -height / 2, 0, 0, -1, *color,
            -length / 2, width / 2, -height / 2, -1, 0, 0, *color,
            -length / 2, width / 2, -height / 2, 0, 1, 0, *color,
            length / 2, width / 2, height / 2, 0, 0, 1, *color,
            length / 2, width / 2, height / 2, 1, 0, 0, *color,
            length / 2, width / 2, height / 2, 0, 1, 0, *color,
            length / 2, width / 2, -height / 2, 0, 0, -1, *color,
            length / 2, width / 2, -height / 2, 1, 0, 0, *color,
            length / 2, width / 2, -height / 2, 0, 1, 0, *color,
            -length / 2, -width / 2, height / 2, 0, 0, 1, *color,
            -length / 2, -width / 2, height / 2, -1, 0, 0, *color,
            -length / 2, -width / 2, height / 2, 0, -1, 0, *color,
            -length / 2, -width / 2, -height / 2, 0, 0, -1, *color,
            -length / 2, -width / 2, -height / 2, -1, 0, 0, *color,
            -length / 2, -width / 2, -height / 2, 0, -1, 0, *color,
            length / 2, -width / 2, height / 2, 0, 0, 1, *color,
            length / 2, -width / 2, height / 2, 1, 0, 0, *color,
            length / 2, -width / 2, height / 2, 0, -1, 0, *color,
            length / 2, -width / 2, -height / 2, 0, 0, -1, *color,
            length / 2, -width / 2, -height / 2, 1, 0, 0, *color,
            length / 2, -width / 2, -height / 2, 0, -1, 0, *color
        ]).reshape((24, 9))

        self.vertices[0:24, 0:9] = vl

        self.indices = np.zeros([12, 3])

        ind = np.array([
            0, 18, 6,
            0, 12, 18,
            14, 17, 20,
            20, 17, 23,
            15, 3, 9,
            15, 9, 21,
            5, 2, 11,
            11, 2, 8,
            4, 16, 1,
            1, 16, 13,
            7, 19, 10,
            10, 19, 22
        ]).reshape((12, 3))

        self.indices[0:12, 0:3] = ind

    def draw(self):
        """
            Draw the actual Cube.

            :return: None.
        """
        self.vao.bind()
        self.ebo.draw()
        self.vao.unbind()

    def initialize(self):
        """
            Initialize the VBO/EBO Settings.

            :return: None.
        """
        self.vao.bind()
        self.vbo.setBuffer(self.vertices, 11)
        self.ebo.setBuffer(self.indices)

        self.vbo.setAttribPointer(self.shaderProg.getAttribLocation("vertexPos"),
                                  stride=11, offset=0, attribSize=3)
        self.vbo.setAttribPointer(self.shaderProg.getAttribLocation("vertexNormal"),
                                  stride=11, offset=3, attribSize=3)
        self.vbo.setAttribPointer(self.shaderProg.getAttribLocation("vertexColor"),
                                  stride=11, offset=6, attribSize=3)
        self.vao.unbind()
