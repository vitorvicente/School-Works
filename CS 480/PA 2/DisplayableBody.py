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
    Define the Main Body of our Octopus.
    Extends the Displayable Class.
"""


class DisplayableBody(Displayable):
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
        self.callListHandle = gl.glGenLists(2)
        self.qd = glu.gluNewQuadric()

        gl.glNewList(self.callListHandle, gl.GL_COMPILE)

        # Main Section of the Body
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glTranslate(0, 0.6, -0.4)
        gl.glRotate(60, 1, 0, 0)

        gl.glScale(*[1, 1, 1.5])
        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()

        # Back of the Head
        gl.glPushMatrix()
        gl.glScale(*self.scale)
        gl.glTranslate(0, 1, -1)

        gl.glScale(*[1, 1.1, 1.2])

        glu.gluSphere(self.qd, 0.7, 30, 30)

        gl.glPopMatrix()

        gl.glEndList()
