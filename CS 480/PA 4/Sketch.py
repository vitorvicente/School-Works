"""
    This is the main entry of your program. Almost all things you need to implement are in this file.
    The main class Sketch inherits from CanvasBase. For the parts you need to implement, they are all marked with TODO.
    First version Created on 09/28/2018. Last version Created on 12/06/2021

    :author: micou(Zezhou Sun), vitor@bu.edu.
    :version: 2021.12.06.
"""
import math

import numpy as np

import ColorType
from Animation import Animation
from ModelAxes import ModelAxes
from Point import Point
from CanvasBase import CanvasBase
from GLProgram import GLProgram
import GLUtility
from SceneFour import SceneFour
from SceneOne import SceneOne
from SceneThree import SceneThree
from SceneTwo import SceneTwo

try:
    import wx
    from wx import glcanvas
except ImportError:
    raise ImportError("Required dependency wxPython not present")
try:
    from PIL import Image
except:
    print("Need to install PIL package. Pip package name is Pillow")
    raise ImportError
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


class Sketch(CanvasBase):
    """
        Drawing methods and interrupt methods will be implemented in this class.
    
        Variable Instruction:
            * debug(int): Define debug level for log printing

            * 0 for stable version, minimum log is printed
            * 1 will print general logs for lines and triangles
            * 2 will print more details and do some type checking, which might be helpful in debugging

        
        Method Instruction:
        
        
        Here are the list of functions you need to override:
            * Interrupt_MouseL: Used to deal with mouse click interruption. Canvas will be refreshed with updated buff
            * Interrupt_MouseLeftDragging: Used to deal with mouse dragging interruption.
            * Interrupt_Keyboard: Used to deal with keyboard press interruption. Use this to add new keys or new methods
    """
    context = None

    debug = 1

    last_mouse_leftPosition = None
    last_mouse_middlePosition = None
    components = None

    texture = None
    shaderProg = None
    glutility = None

    frameCount = 0

    lookAtPt = None
    upVector = None
    backgroundColor = None

    cameraDis = None
    cameraTheta = None
    cameraPhi = None

    viewMat = None
    perspMat = None

    pauseScene = False

    basisAxes = None
    scene = None

    # Current Scene in the Scene List
    sceneIndex = 0

    def __init__(self, parent):
        """
            Init everything. You should set your model here.
        """
        super(Sketch, self).__init__(parent)

        contextAttrib = glcanvas.GLContextAttrs()
        contextAttrib.PlatformDefaults().CoreProfile().MajorVersion(3).MinorVersion(3).EndList()
        self.context = glcanvas.GLContext(self, ctxAttrs=contextAttrib)

        self.last_mouse_leftPosition = [0, 0]
        self.last_mouse_middlePosition = [0, 0]
        self.components = []
        self.backgroundColor = ColorType.BLUEGREEN

        self.resetView()

        self.glutility = GLUtility.GLUtility()

    def resetView(self):
        """
            Reset Camera View.

            :return: None.
        """
        self.lookAtPt = [0, 0, 0]
        self.upVector = [0, 1, 0]
        self.cameraDis = 6
        self.cameraPhi = math.pi / 6
        self.cameraTheta = math.pi / 2

    def switchScene(self, scene):
        """
            Switch Current Scene.

            :return: None.
        """
        self.scene = scene
        self.topLevelComponent.clear()
        self.topLevelComponent.addChild(self.scene)
        self.topLevelComponent.initialize()

    def InitGL(self):
        """
            Init the GL Engine.

            :return: None.
        """
        self.shaderProg = GLProgram()
        self.shaderProg.compile()

        self.basisAxes = ModelAxes(self.shaderProg, Point((0, 0, 0)))
        self.basisAxes.initialize()

        self.sceneIndex = 0
        self.switchScene(SceneOne(self.shaderProg))

        gl.glClearColor(*self.backgroundColor, 1.0)
        gl.glClearDepth(1.0)
        gl.glViewport(0, 0, self.size[0], self.size[1])

        gl.glEnable(gl.GL_DEPTH_TEST)

        self.perspMat = self.glutility.perspective(45, self.size.width, self.size.height, 0.01, 100)
        self.shaderProg.setMat4("projectionMat", self.perspMat)
        self.shaderProg.setMat4("viewMat", self.glutility.view(self.getCameraPos(), self.lookAtPt, self.upVector))
        self.shaderProg.setMat4("modelMat", np.identity(4))

        self.shaderProg.setVec3("viewPosition", np.array(self.getCameraPos()))

    def getCameraPos(self):
        """
            Retrieve the current Position of the Camera.

            :return: None.
        """
        ct = math.cos(self.cameraTheta)
        st = math.sin(self.cameraTheta)
        cp = math.cos(self.cameraPhi)
        sp = math.sin(self.cameraPhi)
        result = [self.lookAtPt[0] + self.cameraDis * ct * cp,
                  self.lookAtPt[1] + self.cameraDis * sp,
                  self.lookAtPt[2] + self.cameraDis * st * cp]
        return result

    def OnResize(self, event):
        """
            React to Window Resize.

            :return: None.
        """
        contextAttrib = glcanvas.GLContextAttrs()
        contextAttrib.PlatformDefaults().CoreProfile().MajorVersion(3).MinorVersion(3).EndList()
        self.context = glcanvas.GLContext(self, ctxAttrs=contextAttrib)
        self.size = self.GetClientSize()
        self.size[1] = max(1, self.size[1])  # avoid divided by 0
        self.SetCurrent(self.context)

        self.init = False
        self.Refresh(eraseBackground=True)
        self.Update()

    def OnPaint(self, event=None):
        """
            Paint the Frame Window, using GL.

            :return: None.
        """
        self.SetCurrent(self.context)
        if not self.init:
            self.InitGL()
            self.init = True

        self.OnDraw()

    def OnDraw(self):
        """
            Drawing Cleanup/Setup.

            :return: None.
        """
        gl.glClearColor(*self.backgroundColor, 1.0)
        gl.glClear(gl.GL_COLOR_BUFFER_BIT | gl.GL_DEPTH_BUFFER_BIT)

        self.viewMat = self.glutility.view(self.getCameraPos(), self.lookAtPt, self.upVector)
        self.shaderProg.setMat4("viewMat", self.viewMat)
        self.shaderProg.setVec3("viewPosition", np.array(self.getCameraPos()))

        if not self.pauseScene and isinstance(self.scene, Animation):
            self.scene.animationUpdate()
        self.topLevelComponent.update(np.identity(4))
        self.topLevelComponent.draw(self.shaderProg)

        resultPt = self.unprojectCanvas(0.9 * self.size[0], 0.1 * self.size[1], 0.3)
        self.basisAxes.setCurrentPosition(resultPt)
        self.basisAxes.draw(self.shaderProg)

        self.SwapBuffers()

    def OnDestroy(self, event):
        """
            Window destroy event binding.

            :param event: Window destroy event.
            :return: None.
        """
        if self.shaderProg is not None:
            del self.shaderProg
        super(Sketch, self).OnDestroy(event)

    def Interrupt_Scroll(self, wheelRotation):
        """
            When mouse wheel rotating detected, do following things.

            :param wheelRotation: mouse wheel changes, normally +120 or -120.
            :return: None.
        """
        if wheelRotation == 0:
            return
        wheelChange = wheelRotation / abs(wheelRotation)
        self.cameraDis = max(self.cameraDis - wheelChange * 0.1, 0.01)
        self.update()

    def unprojectCanvas(self, x, y, u=0.5):
        """
            Unproject a canvas point to world coordiantes. 2D -> 3D
            you need give an extra parameter u, to tell the method how far are you from znear
            u is the proportion of distance to znear / zfar-znear
            in the gluUnProject, the distribution of z is not linear when using perspective projection,
            so z=0.5 is not in the middle,
            that's why we compute out the ray and use linear interpolation and u to get the point.

            :param u: u is the proportion to the znear/, in range [0, 1].
            :type u: float.
            :return: Point.
        """
        result1 = glu.gluUnProject(x, y, 0.0,
                                   np.identity(4),
                                   self.viewMat @ self.perspMat,
                                   gl.glGetIntegerv(gl.GL_VIEWPORT))
        result2 = glu.gluUnProject(x, y, 1.0,
                                   np.identity(4),
                                   self.viewMat @ self.perspMat,
                                   # be careful, the concate of view and persp is called projection matrix in opengl
                                   gl.glGetIntegerv(gl.GL_VIEWPORT))
        result = Point([(1 - u) * r1 + u * r2 for r1, r2 in zip(result1, result2)])
        return result

    def Interrupt_MouseL(self, x, y):
        """
            When mouse click detected, store current position in last_mouse_leftPosition.

            :param x: Mouse click's x coordinate.
            :type x: int.
            :param y: Mouse click's y coordinate.
            :type y: int.
            :return: None.
        """
        self.last_mouse_leftPosition[0] = x
        self.last_mouse_leftPosition[1] = y

    def Interrupt_MouseMiddleDragging(self, x, y):
        """
            When mouse drag motion with middle key detected, interrupt with new mouse position.

            :param x: Mouse drag new position's x coordinate.
            :type x: int.
            :param y: Mouse drag new position's x coordinate.
            :type y: int.
            :return: None.
        """
        dx = x - self.last_mouse_middlePosition[0]
        dy = y - self.last_mouse_middlePosition[1]

        originalMidPt = self.unprojectCanvas(*self.last_mouse_middlePosition, 0.5)

        self.last_mouse_middlePosition[0] = x
        self.last_mouse_middlePosition[1] = y
        if dx * dx + dy * dy > 5:
            return

        currentMidPt = self.unprojectCanvas(x, y, 0.5)
        changes = currentMidPt - originalMidPt
        moveSpeed = 0.185 * self.cameraDis / 6
        self.lookAtPt = [self.lookAtPt[0] - changes[0] * moveSpeed,
                         self.lookAtPt[1] - changes[1] * moveSpeed,
                         self.lookAtPt[2] - changes[2] * moveSpeed]

    def Interrupt_MouseLeftDragging(self, x, y):
        """
            When mouse drag motion detected, interrupt with new mouse position.

            :param x: Mouse drag new position's x coordinate.
            :type x: int.
            :param y: Mouse drag new position's x coordinate.
            :type y: int.
            :return: None.
        """
        dx = x - self.last_mouse_leftPosition[0]
        dy = y - self.last_mouse_leftPosition[1]

        if dx * dx + dy * dy > 5:
            self.last_mouse_leftPosition[0] = x
            self.last_mouse_leftPosition[1] = y
            return

        self.cameraPhi = min(math.pi / 2, max(-math.pi / 2, self.cameraPhi - dy / 100))
        self.cameraTheta += dx / 100

        self.cameraPhi = (self.cameraPhi + math.pi) % (2 * math.pi) - math.pi
        self.cameraTheta = self.cameraTheta % (2 * math.pi)

        self.last_mouse_leftPosition[0] = x
        self.last_mouse_leftPosition[1] = y

    def updateScene(self):
        """
            Update which scene to Display, based on the current Scene Index.

            :return: None.
        """
        if self.sceneIndex == 0:
            self.switchScene(SceneOne(self.shaderProg))
        elif self.sceneIndex == 1:
            self.switchScene(SceneTwo(self.shaderProg))
        elif self.sceneIndex == 2:
            self.switchScene(SceneThree(self.shaderProg))
        elif self.sceneIndex == 3:
            self.switchScene(SceneFour(self.shaderProg))

    def updateLights(self, lightNum):
        """
            Toggles a specific Light On and Off in the current scene, by adding/removing it from the active Lights list.

            :return: None.
        """
        if lightNum >= len(self.scene.allLights):
            print("Light selected does not exist in the current scene!")
            return

        if self.scene.lights.__contains__(self.scene.allLights[lightNum]):
            self.scene.lights.remove(self.scene.allLights[lightNum])
            self.scene.lightCubes.remove(self.scene.allLightCubes[lightNum])
            self.scene.children.remove(self.scene.allLightCubes[lightNum])
        else:
            self.scene.lights.append(self.scene.allLights[lightNum])
            self.scene.lightCubes.append(self.scene.allLightCubes[lightNum])
            self.scene.children.append(self.scene.allLightCubes[lightNum])

    def updateLightType(self, lightTypeNum):
        """
            Toggles on and Off a specific Light Type from all Lights in the current Light List.

            :return: None.
        """
        for i, light in enumerate(self.scene.lights):
            if lightTypeNum == 0:
                light.toggleSpecular()
            elif lightTypeNum == 1:
                light.toggleDiffuse()
            else:
                light.toggleAmbient()

    def toggleNormal(self):
        """
            Toggles On and Off the Normal Debug View.

            :return: None.
        """
        for comp in self.scene.children:
            if self.scene.lightCubes.__contains__(comp):
                continue
            elif comp.renderingRouting == "lighting":
                comp.renderingRouting = "normal"
            else:
                comp.renderingRouting = "lighting"

    def update(self):
        """
            Update current canvas.

            :return: None.
        """
        self.topLevelComponent.update(np.identity(4))

    def Interrupt_Keyboard(self, keycode):
        """
            Keyboard interrupt bindings.

            :param keycode: wxpython keyboard event's keycode.
            :return: None.
        """
        if keycode in [wx.WXK_RETURN]:
            self.update()
        if keycode in [wx.WXK_LEFT]:
            if self.sceneIndex == 0:
                self.sceneIndex = 3
            else:
                self.sceneIndex -= 1

            self.updateScene()
            self.update()
        if keycode in [wx.WXK_RIGHT]:
            if self.sceneIndex == 3:
                self.sceneIndex = 0
            else:
                self.sceneIndex += 1

            self.updateScene()
            self.update()
        if keycode in [wx.WXK_UP]:
            self.Interrupt_Scroll(1)
            self.update()
        if keycode in [wx.WXK_DOWN]:
            self.Interrupt_Scroll(-1)
            self.update()
        if keycode in [49]:
            self.updateLights(0)
            self.scene.initialize()
            self.update()
        if keycode in [50]:
            self.updateLights(1)
            self.scene.initialize()
            self.update()
        if keycode in [51]:
            self.updateLights(2)
            self.scene.initialize()
            self.update()
        if keycode in [52]:
            self.updateLights(3)
            self.scene.initialize()
            self.update()
        if chr(keycode) in "aA":
            self.updateLightType(2)
            self.scene.initialize()
            self.update()
        if chr(keycode) in "dD":
            self.updateLightType(1)
            self.scene.initialize()
            self.update()
        if chr(keycode) in "sS":
            self.updateLightType(0)
            self.scene.initialize()
            self.update()
        if chr(keycode) in "nN":
            self.toggleNormal()
            self.update()
        if chr(keycode) in "rR":
            self.resetView()
        if chr(keycode) in "pP":
            self.pauseScene = not self.pauseScene


if __name__ == "__main__":
    """
        Main Program Method, makes use of the wxFrame and Sketch Classes.
    """
    print("This is the main entry! ")
    app = wx.App(False)
    frame = wx.Frame(None, size=(500, 500), title="Test",
                     style=wx.DEFAULT_FRAME_STYLE | wx.FULL_REPAINT_ON_RESIZE)
    canvas = Sketch(frame)

    frame.Show()
    app.MainLoop()
