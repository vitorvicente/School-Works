"""
    Shared File for all the Creature Components used to design our different Creatures.

    :author: vitor@bu.edu
    :version: 1.0
"""
import random

from Component import Component
from Point import Point
import ColorType as Ct
from Displayable import Displayable
from Animation import Animation
from EnvironmentObject import EnvironmentObject
from Vivarium import Tank

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


"""
    Class for the Displayable Fish Body Component. 
    Represents only the Main Body of the Fish Creature.
"""
class DisplayableFishBody(Displayable):
    callListHandle = 0
    qd = None
    _bufferData = None

    """
        Init Method for the Displayable Component.
    """
    def __init__(self, parent):
        super().__init__(parent)
        parent.context.SetCurrent(parent)

    """
        Draw Method to actually Draw the Component according to the CallList defined at Initialization.
    """
    def draw(self):
        gl.glCallList(self.callListHandle)

    """
        Initialization Method to initialize the CallList to Draw the Component. 
        Draws the Body of the Fish Creature.
    """
    def initialize(self):
        self.callListHandle = gl.glGenLists(1)
        self.qd = glu.gluNewQuadric()

        gl.glNewList(self.callListHandle, gl.GL_COMPILE)
        gl.glPushMatrix()

        gl.glScale(*[0.15, 0.25, 0.5])
        gl.glScale(*[0.5, 0.5, 0.5])

        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()
        gl.glEndList()


"""
    Class for the Displayable Shark Body Component. 
    Represents only the Main Body of the Shark Creature.
"""
class DisplayableSharkBody(Displayable):
    callListHandle = 0
    qd = None
    _bufferData = None

    """
        Init Method for the Displayable Component.
    """
    def __init__(self, parent):
        super().__init__(parent)
        parent.context.SetCurrent(parent)

    """
        Draw Method to actually Draw the Component according to the CallList defined at Initialization.
    """
    def draw(self):
        gl.glCallList(self.callListHandle)

    """
        Initialization Method to initialize the CallList to Draw the Component. 
        Draws the Body of the Shark Creature.
    """
    def initialize(self):
        self.callListHandle = gl.glGenLists(1)
        self.qd = glu.gluNewQuadric()

        gl.glNewList(self.callListHandle, gl.GL_COMPILE)
        gl.glPushMatrix()

        gl.glScale(*[0.15, 0.25, 0.5])
        gl.glScale(*[1, 1, 1])

        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()
        gl.glEndList()


"""
    Class for the Displayable Shark Fin Component. 
    Represents only the Side Fin of the Shark Creature.
"""
class DisplayableSharkFin(Displayable):
    callListHandle = 0
    qd = None
    _bufferData = None

    """
        Init Method for the Displayable Component.
    """
    def __init__(self, parent, side):
        super().__init__(parent)
        parent.context.SetCurrent(parent)
        self.side = side

    """
        Draw Method to actually Draw the Component according to the CallList defined at Initialization.
    """
    def draw(self):
        gl.glCallList(self.callListHandle)

    """
        Initialization Method to initialize the CallList to Draw the Component. 
        Draws the Fin of the Shark Creature.
    """
    def initialize(self):
        self.callListHandle = gl.glGenLists(1)
        self.qd = glu.gluNewQuadric()

        gl.glNewList(self.callListHandle, gl.GL_COMPILE)
        gl.glPushMatrix()

        gl.glRotate(-20, 1, 0, 0)
        gl.glRotate(self.side * 40, 0, 1, 0)

        gl.glScale(*[0.5, 0.15, 0.25])
        gl.glTranslate(self.side * 0.2, -0.1, 0.2)
        gl.glScale(*[0.5, 0.5, 0.5])

        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()
        gl.glEndList()


"""
    Class for the Displayable Fish Tail Component. 
    Represents only the Tail of the Fish Creature.
"""
class DisplayableFishTail(Displayable):
    callListHandle = 0
    qd = None
    _bufferData = None

    """
        Init Method for the Displayable Component.
    """
    def __init__(self, parent):
        super().__init__(parent)
        parent.context.SetCurrent(parent)

    """
        Draw Method to actually Draw the Component according to the CallList defined at Initialization.
    """
    def draw(self):
        gl.glCallList(self.callListHandle)

    """
        Initialization Method to initialize the CallList to Draw the Component. 
        Draws the Tail of the Fish Creature.
    """
    def initialize(self):
        self.callListHandle = gl.glGenLists(1)
        self.qd = glu.gluNewQuadric()
        gl.glNewList(self.callListHandle, gl.GL_COMPILE)

        gl.glPushMatrix()

        gl.glRotate(10, 1, 0, 0)
        gl.glScale(*[0.15, 0.25, 0.5])
        gl.glTranslate(0, 0.03, -0.3)
        gl.glScale(*[0.25, 0.25, 0.25])

        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()
        gl.glPushMatrix()

        gl.glRotate(-10, 1, 0, 0)
        gl.glScale(*[0.15, 0.25, 0.5])
        gl.glTranslate(0, -0.03, -0.3)
        gl.glScale(*[0.25, 0.25, 0.25])

        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()
        gl.glEndList()


"""
    Class for the Displayable Shark Tail Component. 
    Represents only the Tail of the Shark Creature.
"""
class DisplayableSharkTail(Displayable):
    callListHandle = 0
    qd = None
    _bufferData = None

    """
        Init Method for the Displayable Component.
    """
    def __init__(self, parent, scale=None):
        super().__init__(parent)
        parent.context.SetCurrent(parent)
        if scale is None:
            scale = [1, 1, 1]
        self.scale = scale

    """
        Draw Method to actually Draw the Component according to the CallList defined at Initialization.
    """
    def draw(self):
        gl.glCallList(self.callListHandle)

    """
        Initialization Method to initialize the CallList to Draw the Component. 
        Draws the Tail of the Shark Creature.
    """
    def initialize(self):
        self.callListHandle = gl.glGenLists(1)
        self.qd = glu.gluNewQuadric()
        gl.glNewList(self.callListHandle, gl.GL_COMPILE)

        gl.glPushMatrix()

        gl.glRotate(10, 1, 0, 0)
        gl.glScale(*[0.15, 0.25, 0.5])
        gl.glTranslate(0, 0.03, -0.6)
        gl.glScale(*[0.5, 0.5, 0.5])

        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()
        gl.glPushMatrix()

        gl.glRotate(-10, 1, 0, 0)
        gl.glScale(*[0.15, 0.25, 0.5])
        gl.glTranslate(0, -0.03, -0.6)
        gl.glScale(*[0.5, 0.5, 0.5])

        glu.gluSphere(self.qd, 0.6, 30, 30)

        gl.glPopMatrix()
        gl.glEndList()
