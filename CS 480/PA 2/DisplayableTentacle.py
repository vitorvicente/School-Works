"""
Object representing a single Tentacle of the Octopus

:author: vitor@bu.edu
:version: 1
"""

try:
    import wx
    from wx import glcanvas
except ImportError:
    raise ImportError("Required dependency wxPython not present")

try:
    import OpenGL

    try:
        import OpenGL.GL as gl
        import OpenGL.GLU as glu
        import OpenGL.GLUT as glut
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
        import OpenGL.GLUT as glut
except ImportError:
    raise ImportError("Required dependency PyOpenGL not present")

try:
    from PIL import Image
except:
    print("Need to install PIL package. Pip package name is Pillow")
    raise ImportError

from Displayable import Displayable

"""
    Define a single Tentacle of our Octopus.
    Extends the Displayable Class.
"""


class DisplayableTentacle(Displayable):
    callListHandle = 0
    qd = None
    _bufferData = None

    """
        Init Method.

        :param parent: Parent Component.
        :type parent: Component.
        :param baseRotation: Base rotation of the limb (along the Body).
        :type baseRotation: Integer.
        :param scale: Overall Scale Matrix.
        :type scale: 1x3 Integer Matrix.
        :param rotationValues: Values for the individual Rotation of the Limbs.
        :type rotationValues 3x3 Integer Matrix.
        :return: None.
    """

    def __init__(self, parent, baseRotation, scale, rotationValues):
        super().__init__(parent)
        parent.context.SetCurrent(parent)
        self.baseRotation = baseRotation
        self.scale = scale
        self.rotationValues = rotationValues

    """
        Draw method, actually draws the Object.
    """

    def draw(self):
        gl.glCallList(self.callListHandle)

    """
        Initialize Method, initializes the polygonal components of the Object.
    """

    def initialize(self):
        self.callListHandle = gl.glGenLists(3)
        self.qd = glu.gluNewQuadric()

        gl.glNewList(self.callListHandle, gl.GL_COMPILE)

        # Section 1 & Joints
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glRotate(self.baseRotation, 0, 1, 0)
        gl.glRotate(50, 1, 0, 0)

        gl.glRotate(self.rotationValues[0][0], 1, 0, 0)
        gl.glRotate(self.rotationValues[0][1], 0, 1, 0)
        gl.glRotate(self.rotationValues[0][2], 0, 0, 1)

        glu.gluCylinder(self.qd, 0.2, 0.2, 0.9, 30, 30)

        gl.glPopMatrix()

        # Section 2 & Joints
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glRotate(self.baseRotation, 0, 1, 0)
        gl.glRotate(50, 1, 0, 0)

        gl.glRotate(self.rotationValues[0][0], 1, 0, 0)
        gl.glRotate(self.rotationValues[0][1], 0, 1, 0)
        gl.glRotate(self.rotationValues[0][2], 0, 0, 1)

        gl.glTranslate(0, 0, 0.8)

        glu.gluSphere(self.qd, 0.21, 30, 30)

        gl.glRotate(self.rotationValues[1][0], 1, 0, 0)
        gl.glRotate(self.rotationValues[1][1], 0, 1, 0)
        gl.glRotate(self.rotationValues[1][2], 0, 0, 1)

        glu.gluCylinder(self.qd, 0.2, 0.2, 0.6, 30, 30)

        gl.glPopMatrix()

        # Section 3 & Joints
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glRotate(self.baseRotation, 0, 1, 0)
        gl.glRotate(50, 1, 0, 0)

        gl.glRotate(self.rotationValues[0][0], 1, 0, 0)
        gl.glRotate(self.rotationValues[0][1], 0, 1, 0)
        gl.glRotate(self.rotationValues[0][2], 0, 0, 1)

        gl.glTranslate(0, 0, 0.8)

        gl.glRotate(self.rotationValues[1][0], 1, 0, 0)
        gl.glRotate(self.rotationValues[1][1], 0, 1, 0)
        gl.glRotate(self.rotationValues[1][2], 0, 0, 1)

        gl.glTranslate(0, 0, 0.6)

        glu.gluSphere(self.qd, 0.21, 30, 30)

        gl.glRotate(self.rotationValues[2][0], 1, 0, 0)
        gl.glRotate(self.rotationValues[2][1], 0, 1, 0)
        gl.glRotate(self.rotationValues[2][2], 0, 0, 1)

        glu.gluCylinder(self.qd, 0.2, 0.2, 0.6, 30, 30)

        gl.glTranslate(0, 0, 0.6)

        glu.gluSphere(self.qd, 0.21, 30, 30)

        gl.glPopMatrix()

        gl.glEndList()
