"""
This is the main entry of your program. Almost all things you need to implement are in this file.
The main class Sketch inherits from CanvasBase.

:author: vitor@bu.edu
:version: 1
"""
import os
import wx
import time
import math
import random
import numpy as np

from Point import Point
from ColorType import ColorType
from Quaternion import Quaternion
from CanvasBase import CanvasBase
from ModelOctopus import ModelOctopus
from ModelAxes import ModelAxes

try:
    import wx
    from wx import glcanvas
except ImportError:
    raise ImportError("Required dependency wxPython not present")
try:
    # From pip package "Pillow"
    from PIL import Image
except:
    print("Need to install PIL package. Pip package name is Pillow")
    raise ImportError
try:
    import OpenGL

    try:
        import OpenGL.GL as gl
        import OpenGL.GLU as glu
        import OpenGL.GLUT as glut  # this fails on OS X 11.x
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

"""
    Main Sketch Object, all drawing will occur from this class, extends the CanvasBase class.
    
    :var context: GL Context.
    :type context: GLContext.
    :var debug: Debug Level.
    :type debug: Integer.
    :var last_mouse_leftPosition: Coordinates for the last position of a Left Click.
    :type last_mouse_leftPosition: 1x2 Integer Matrix.
    :var components: List of all Components from Objects to be displayed.
    :type components: List<Component>.
    :var active_components: List of all Actively Selected Components.
    :type active_components: List<Component>.
    :var select_obj_index: Index of the Currently Selected Component in the overall Component List.
    :type select_obj_index: Integer.
    :var select_axis_index: Index of the Currently Selected Axis for rotation.
    :type select_axis_index: Integer.
    :var select_pose_index: Index of the Currently Select Pose.
    :type select_pose_index: Integer.
    :var select_color: Array of Color types, first three for Axis, final for Currently Selected Component.
    :type select_color: 1x3 ColorType Matrix.
"""
class Sketch(CanvasBase):

    context = None

    debug = 1

    last_mouse_leftPosition = None
    components = None
    active_components = None
    select_obj_index = -1
    select_axis_index = 0
    select_pose_index = -1
    select_color = [ColorType(1, 0, 0), ColorType(0, 1, 0), ColorType(0, 0, 1), ColorType(0, 1, 1)]

    """
        Init everything. Including our model.
        
        :return: None.
    """
    def __init__(self, parent):
        super(Sketch, self).__init__(parent)
        self.context = glcanvas.GLContext(self)
        self.last_mouse_leftPosition = [0, 0]

        m1 = ModelAxes(self, Point((-1, -1, -1)))
        m2 = ModelOctopus(self, Point((0, 0, 0)))

        self.topLevelComponent.addChild(m1)
        self.topLevelComponent.addChild(m2)

        self.components = m1.components + m2.components
        self.active_components = []

        self.printInstructions()

    """
        Helper method to print out instructions for how to mess with the Model.
        
        :return: None.
    """
    def printInstructions(self):
        print()
        print("=============================================================================")
        print("Welcome to the Octopus Simulation!")
        print()
        print("In front of you is an Octopus, designed with OpenGL,")
        print("you can play around the frame and control the Octopus")
        print("using the key commands below:")
        print()
        print(" - Dragging with Left Mouse: Change View of the Window.")
        print(" - Left Mouse Click: Update Current Mouse Position.")
        print(" - Scroll: Rotate currently Active Components.")
        print(" - Mouse Moving: Update Eye Tracking (NOT ENABLED).")
        print()
        print(" - Shift + Tab: Cycle through Components.")
        print(" - Tab: Add currently Selected Component to Active Components.")
        print(" - Delete: Remove currently Selected Component from Active Components.")
        print(" - Up Arrow: Scroll in the Positive Direction.")
        print(" - Down Arrow: Scroll in the Negative Direction.")
        print(" - Left Arrow: Cycle to the prior Rotation Axis.")
        print(" - Right Arrow: Cycle to the next Rotation Axis.")
        print()
        print(" - 't': Cycle to the prior Pre-Set Pose.")
        print(" - 'T': Cycle to the next Pre-Set Pose.")
        print(" - 'r': Reset the Viewing Angle.")
        print(" - 'R': Reset everything.")
        print("=============================================================================")
        print()

    """
        When mouse wheel rotating detected, do following things.

        :param wheelRotation: mouse wheel changes, normally +120 or -120.
        :return: None.
    """
    def Interrupt_Scroll(self, wheelRotation):
        wheelChange = wheelRotation / abs(wheelRotation)  # normalize wheel change
        if len(self.components) > self.select_obj_index >= 0:
            for comp in self.active_components:
                comp.rotate(wheelChange * 5, comp.axisBucket[self.select_axis_index])

        self.update()

    """
        When mouse click detected, store current position in last_mouse_leftPosition.

        :param x: Mouse click's x coordinate.
        :type x: int.
        :param y: Mouse click's y coordinate.
        :type y: int.
        :return: None.
    """
    def Interrupt_MouseL(self, x, y):
        self.last_mouse_leftPosition[0] = x
        self.last_mouse_leftPosition[1] = y

    """
        When mouse drag motion detected, interrupt with new mouse position.

        :param x: Mouse drag new position's x coordinate.
        :type x: int.
        :param y: Mouse drag new position's x coordinate.
        :type y: int.
        :return: None.
    """
    def Interrupt_MouseLeftDragging(self, x, y):
        dx = x - self.last_mouse_leftPosition[0]
        dy = y - self.last_mouse_leftPosition[1]
        mag = math.sqrt(dx * dx + dy * dy)
        axis = (dy / mag, -dx / mag, 0) if mag != 0 else (1, 0, 0)
        viewing_delta = 3.14159265358 / 180
        s = math.sin(0.5 * viewing_delta)
        c = math.cos(0.5 * viewing_delta)
        q = Quaternion(c, s * axis[0], s * axis[1], s * axis[2])
        self.viewing_quaternion = q.multiply(self.viewing_quaternion)
        self.viewing_quaternion.normalize()  # to correct round-off error caused by cos/sin
        self.last_mouse_leftPosition[0] = x
        self.last_mouse_leftPosition[1] = y

    """
        Method to implement the movement of the Creature Eyes.

        Extra Points, not implemented.

        :param x: Current X Coordinate of the Mouse.
        :type x: int.
        :param y: Current Y Coordinate of the Mouse.
        :type y: int.
        :return: None.
    """

    def Interrupt_MouseMoving(self, x, y):
        pass

    """
        Keyboard interrupt bindings.
        
        Shift + Enter: Cycle through each component.
        Enter: Add the currently selected component to the Active Components.
        Backspace: Remove the currently selected component from the Active Components.
        Left Key: Cycle to the prior Rotation Axis.
        Right Key: Cycle to the next Rotation Axis.
        Up Key: Rotate in the Positive Direction.
        Down Key: Rotate in the Negative Direction.
        "t": Go to the Previous Pre-Set Pose.
        "T": Go to the Next Pre-Set Pose.
        "r": Reset the Viewing Angle.
        "R": Reset everything.

        :param keycode: wxpython keyboard event's keycode.
        :return: None.
    """
    def Interrupt_Keyboard(self, keycode):
        if keycode == 10:
            if len(self.components) > self.select_obj_index >= 0:
                if not self.components[self.select_obj_index] in self.active_components:
                    self.components[self.select_obj_index].reset("color")
                else:
                    if self.select_axis_index == -1:
                        self.select_axis_index = 0
                    self.components[self.select_obj_index].setCurrentColor(self.select_color[self.select_axis_index])

            if len(self.components) > 0:
                if self.select_obj_index < 0:
                    self.select_obj_index = 0
                else:
                    self.select_obj_index = (self.select_obj_index + 1) % len(self.components)

            if len(self.components) > self.select_obj_index >= 0:
                self.components[self.select_obj_index].setCurrentColor(self.select_color[3])
            self.update()
        if keycode == 8:
            if len(self.components) > self.select_obj_index >= 0:
                self.components[self.select_obj_index].setCurrentColor(self.select_color[3])
                self.active_components.remove(self.components[self.select_obj_index])
            self.update()
        if keycode in [wx.WXK_RETURN]:
            if len(self.components) > self.select_obj_index >= 0:
                self.components[self.select_obj_index].setCurrentColor(self.select_color[self.select_axis_index])
                self.active_components.append(self.components[self.select_obj_index])
            self.update()
        if keycode in [wx.WXK_LEFT]:
            # Last rotation axis of this component
            self.select_axis_index = (self.select_axis_index - 1) % 3
            if len(self.components) > self.select_obj_index >= 0:
                for comp in self.active_components:
                    comp.setCurrentColor(self.select_color[self.select_axis_index])
            self.update()
        if keycode in [wx.WXK_RIGHT]:
            # Next rotation axis of this component
            self.select_axis_index = (self.select_axis_index + 1) % 3
            if len(self.components) > self.select_obj_index >= 0:
                for comp in self.active_components:
                    comp.setCurrentColor(self.select_color[self.select_axis_index])
            self.update()
        if keycode in [wx.WXK_UP]:
            # Increase rotation angle
            self.Interrupt_Scroll(1)
            self.update()
        if keycode in [wx.WXK_DOWN]:
            # Decrease rotation angle
            self.Interrupt_Scroll(-1)
            self.update()
        if keycode in [wx.WXK_ESCAPE]:
            # exit component editing mode
            if len(self.components) > self.select_obj_index >= 0:
                self.components[self.select_obj_index].reset("color")
            self.select_obj_index = -1
            self.select_axis_index = -1
            self.update()
        if chr(keycode) in "t":
            self.select_pose_index = (self.select_pose_index - 1) % 5
            self.movePoses()
            self.update()
        if chr(keycode) in "T":
            self.select_pose_index = (self.select_pose_index + 1) % 5
            self.movePoses()
            self.update()
        if chr(keycode) in "r":
            # reset viewing angle only
            self.viewing_quaternion = Quaternion()
        if chr(keycode) in "R":
            # reset everything
            for c in self.components:
                c.reset()
            self.viewing_quaternion = Quaternion()
            self.select_obj_index = 0
            self.select_axis_index = 0
            self.select_pose_index = 0
            self.active_components = []
            self.update()

    """
        Update current canvas.

        :return: None.
    """

    def update(self):
        self.modelUpdate()

    """
        Method to cycle through the pre-set poses for the Octopus.

        :return: None.
    """

    def movePoses(self):
        print("MOVING ", self.select_pose_index)
        self.topLevelComponent.children[1].setPosition(self.select_pose_index)


if __name__ == "__main__":
    app = wx.App(False)
    frame = wx.Frame(None, size=(500, 500), title="Test",
                     style=wx.DEFAULT_FRAME_STYLE | wx.FULL_REPAINT_ON_RESIZE)  # Disable Resize: ^ wx.RESIZE_BORDER
    canvas = Sketch(frame)

    frame.Show()
    app.MainLoop()
