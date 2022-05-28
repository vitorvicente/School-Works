"""
Object representing a single Eye of the Octopus

:author: vitor@bu.edu
:version: 1
"""

import ColorType as Ct

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
    Define the Eyes of our Octopus.
    Extends the Displayable Class.
"""


class DisplayableEye(Displayable):
    callListHandle = 0
    qd = None
    _bufferData = None

    """
        Init Method.

        :param: parent - Parent Component.
        :ptype: parent - Component.
    """

    def __init__(self, parent, scale=None):
        super().__init__(parent)
        parent.context.SetCurrent(parent)
        if scale is None:
            scale = [1, 1, 1]
        self.scale = scale

    """
        Draw method, actually draws the Object.
    """

    def draw(self):
        gl.glCallList(self.callListHandle)

    """
        Initialize Method, initializes the polygonal components of the Object.
    """

    def initialize(self):
        self.callListHandle = gl.glGenLists(4)
        self.qd = glu.gluNewQuadric()

        gl.glNewList(self.callListHandle, gl.GL_COMPILE)

        # Outer Eye #1
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glTranslate(0.61, 0.6, -0.1)
        gl.glRotate(75, 0, 1, 0)
        gl.glColor3f(*Ct.DARKORANGE3.getRGB())

        glu.gluDisk(self.qd, 0.15, 0.3, 30, 30)

        gl.glTranslate(0, 0, -0.2)
        glu.gluCylinder(self.qd, 0.2, 0.3, 0.2, 30, 30)

        gl.glPopMatrix()

        # Inner Eye #1
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glTranslate(0.61, 0.6, -0.1)
        gl.glRotate(75, 0, 1, 0)
        gl.glColor3f(*Ct.CYAN.getRGB())

        glu.gluDisk(self.qd, 0, 0.2, 30, 30)

        gl.glPopMatrix()

        # Outer Eye #2
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glTranslate(-0.61, 0.6, -0.1)
        gl.glRotate(-75, 0, 1, 0)
        gl.glColor3f(*Ct.DARKORANGE3.getRGB())

        glu.gluDisk(self.qd, 0.15, 0.3, 30, 30)

        gl.glTranslate(0, 0, -0.2)
        glu.gluCylinder(self.qd, 0.2, 0.3, 0.2, 30, 30)

        gl.glPopMatrix()

        # Inner Eye #2
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glTranslate(-0.61, 0.6, -0.1)
        gl.glRotate(-75, 0, 1, 0)
        gl.glColor3f(*Ct.CYAN.getRGB())

        glu.gluDisk(self.qd, 0, 0.2, 30, 30)

        gl.glPopMatrix()

        gl.glEndList()
