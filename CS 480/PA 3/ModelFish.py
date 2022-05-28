"""
    Model for our Fish Creature, and Class Wrapper around it.

    :author: vitor@bu.edu
    :version: 1.0
"""
import math
import random
import numpy

from Component import Component
from Point import Point
import ColorType as Ct
from Animation import Animation
from EnvironmentObject import EnvironmentObject
from DisplayableComponents import DisplayableFishBody, DisplayableFishTail

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
    Actual Model Class for our Fish Creature.
"""
class ModelFish(Component):
    components = None
    contextParent = None

    """
        Init Creature, adding all its Components (Body & Tail).
        Sets Color and Rotation Extent for the Components.
    """
    def __init__(self, parent, position, display_obj=None):
        super().__init__(position, display_obj)
        self.components = []
        self.contextParent = parent

        body = Component(position, DisplayableFishBody(self.contextParent))
        tail = Component(position, DisplayableFishTail(self.contextParent))

        body.setDefaultColor(Ct.DARKORANGE1)
        tail.setDefaultColor(Ct.DARKORANGE3)

        body.setRotateExtent(body.uAxis, -180, 180)
        body.setRotateExtent(body.vAxis, -180, 180)
        body.setRotateExtent(body.wAxis, -180, 180)

        tail.setRotateExtent(tail.uAxis, 0, 0)
        tail.setRotateExtent(tail.wAxis, 0, 0)
        tail.setRotateExtent(tail.vAxis, -10, 10)

        self.addChild(body)
        body.addChild(tail)

        self.components = [body, tail]


"""
    Component Wrapper for our Fish Model.
    Includes Animation & Environment Implementations.
"""
class Fish(Component, Animation, EnvironmentObject):
    components = None
    rotation_speed = None
    translation_speed = None
    tail_rotation = 1

    """
        Init Class Wrapper using the Fish Model.
        Sets basic rules for Animation & Environment Implementations.
    """
    def __init__(self, parent, position, velocity):
        super(Fish, self).__init__(position)
        fish = ModelFish(parent, position)

        self.components = fish.components
        self.addChild(fish)

        self.baseVelocity = velocity
        self.velocity = velocity
        self.uAxis = [1, 0, 0]
        self.vAxis = [0, 1, 0]
        self.wAxis = [0, 0, 1]

        self.bound_center = position
        self.bound_radius = 0.1 * 2
        self.species_id = 1

    """
        Animation Method for our Creature. This includes a lot of stuff, from bounding checks, and direction 
        calculations.
    """
    def animationUpdate(self):
        returnList = []

        # Fetch the velocity 3 out of 10 times (this makes the movement smooth)
        if random.random() > 0.7:
            if len(self.env_obj_list) > 2:
                self.velocity = self.fetchVelocity()
        currPos = self.current_position.getCoords()

        # Creature check
        for c in self.env_obj_list[::-1]:
            otherPos = c.current_position.getCoords()
            dist = math.sqrt((currPos[0] - otherPos[0])**2 + (currPos[1] - otherPos[1])**2 + (currPos[2] - otherPos[
                2])**2)

            if dist <= self.bound_radius + c.bound_radius:
                if c.species_id < self.species_id:
                    returnList.append(c)
                else:
                    for i in range(0, 3):
                        if self.velocity[i] != 0:
                            self.velocity[i] *= -1
                        if c.velocity[i] != 0:
                            c.velocity[i] *= -1

        # Movement calculation
        for i in range(0, 3):
            currPos[i] += self.velocity[i]
            if currPos[i] >= 2 or currPos[i] <= -2:
                if self.velocity[i] != 0:
                    self.velocity[i] *= -1
                    currPos[i] += 10*self.velocity[i]

        # Creature Updates
        self.facePoint()
        self.setCurrentPosition(Point(currPos))
        self.tailSwing()
        self.update()

        return returnList

    """
        Helper Method that uses potential functions to calculate the direction in which the creature moves, 
        includes scenarios for attracting and repulsion, as well as corner cases for the same type of creatures, 
        or with the tank.
    """
    def fetchVelocity(self):
        returnVal = [0.0, 0.0, 0.0]
        multiplier = 1
        for c in self.env_obj_list[::-1]:
            if c.species_id == -1:
                multiplier = 0
            elif c.species_id == self.species_id:
                multiplier = 0
            elif c.species_id > self.species_id:
                multiplier = 1
            elif c.species_id < self.species_id:
                multiplier = -1

            selfPos = self.current_position.getCoords()
            otherPos = c.current_position.getCoords()
            qMinusP = [0.0, 0.0, 0.0]
            pMinusQ = [0.0, 0.0, 0.0]
            for i in range(0, 3):
                qMinusP[i] = (otherPos[i]-selfPos[i])
                pMinusQ[i] = (selfPos[i]-otherPos[i])

            curVals = []
            exponent = -numpy.dot(numpy.array(pMinusQ), numpy.array(pMinusQ))
            for i in range(0, 3):
                curVals.append(2 * pMinusQ[i] * numpy.exp(exponent))
            for i in range(0, 3):
                returnVal[i] += multiplier*curVals[i]

        for i in range(0, 3):
            if returnVal[i] < 0:
                returnVal[i] = -0.01
            elif returnVal[i] > 0:
                returnVal[i] = 0.01
            else:
                returnVal[i] = 0

        if returnVal == [0, 0, 0]:
            returnVal = self.baseVelocity.copy()

        return returnVal

    """
        Helper method to swing the Fish's Tail!
    """
    def tailSwing(self):
        if self.components[1].vAngle == 10 or self.components[1].vAngle == -10:
            self.tail_rotation *= -1
        self.components[1].rotate(self.tail_rotation, self.components[1].vAxis)

    """
        Helper method to make sure the creature is always facing the correct direction. I implemented this very very 
        manually, but it does work!
    """
    def facePoint(self):
        self.reset("angle")

        if self.velocity[0] == 0:
            if self.velocity[1] == 0:
                if self.velocity[2] > 0:
                    pass
                elif self.velocity[2] < 0:
                    self.rotate(180, self.vAxis)
            elif self.velocity[1] > 0:
                if self.velocity[2] == 0.0:
                    self.rotate(90, self.uAxis)
                elif self.velocity[2] > 0:
                    self.rotate(-45, self.uAxis)
                elif self.velocity[2] < 0:
                    self.rotate(45, self.uAxis)
                    self.rotate(180, self.vAxis)
            elif self.velocity[1] < 0:
                if self.velocity[2] == 0:
                    self.rotate(-90, self.uAxis)
                elif self.velocity[2] > 0:
                    self.rotate(45, self.uAxis)
                elif self.velocity[2] < 0:
                    self.rotate(-45, self.uAxis)
                    self.rotate(180, self.vAxis)
        elif self.velocity[0] > 0:
            if self.velocity[1] == 0:
                if self.velocity[2] == 0:
                    self.rotate(90, self.vAxis)
                elif self.velocity[2] > 0:
                    self.rotate(45, self.vAxis)
                elif self.velocity[2] < 0:
                    self.rotate(135, self.vAxis)
            elif self.velocity[1] > 0:
                if self.velocity[2] == 0:
                    self.rotate(-90, self.uAxis)
                    self.rotate(90, self.vAxis)
                    self.rotate(-45, self.vAxis)
                    self.rotate(90, self.wAxis)
                elif self.velocity[2] > 0:
                    self.rotate(-45, self.uAxis)
                    self.rotate(45, self.vAxis)
                elif self.velocity[2] < 0:
                    self.rotate(45, self.uAxis)
                    self.rotate(135, self.vAxis)
            elif self.velocity[1] < 0:
                if self.velocity[2] == 0:
                    self.rotate(90, self.uAxis)
                    self.rotate(90, self.vAxis)
                    self.rotate(-45, self.vAxis)
                    self.rotate(90, self.wAxis)
                elif self.velocity[2] > 0:
                    self.rotate(45, self.uAxis)
                    self.rotate(45, self.vAxis)
                elif self.velocity[2] < 0:
                    self.rotate(-45, self.uAxis)
                    self.rotate(135, self.vAxis)
        elif self.velocity[0] < 0:
            if self.velocity[1] == 0:
                if self.velocity[2] == 0:
                    self.rotate(-90, self.vAxis)
                elif self.velocity[2] > 0:
                    self.rotate(-45, self.vAxis)
                elif self.velocity[2] < 0:
                    self.rotate(-135, self.vAxis)
            elif self.velocity[1] > 0:
                if self.velocity[2] == 0:
                    self.rotate(-90, self.uAxis)
                    self.rotate(-90, self.vAxis)
                    self.rotate(45, self.vAxis)
                    self.rotate(90, self.wAxis)
                elif self.velocity[2] > 0:
                    self.rotate(-45, self.uAxis)
                    self.rotate(-45, self.vAxis)
                elif self.velocity[2] < 0:
                    self.rotate(45, self.uAxis)
                    self.rotate(-135, self.vAxis)
            elif self.velocity[1] < 0:
                if self.velocity[2] == 0:
                    self.rotate(90, self.uAxis)
                    self.rotate(-90, self.vAxis)
                    self.rotate(45, self.vAxis)
                    self.rotate(90, self.wAxis)
                elif self.velocity[2] > 0:
                    self.rotate(45, self.uAxis)
                    self.rotate(-45, self.vAxis)
                elif self.velocity[2] < 0:
                    self.rotate(-45, self.uAxis)
                    self.rotate(-135, self.vAxis)
