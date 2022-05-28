"""
Model for our entire Octopus Creature

:author: vitor@bu.edu
:version: 1
"""

from Component import Component
from Point import Point
import ColorType as Ct
from DisplayableBody import DisplayableBody
from DisplayableEye import DisplayableEye
from ModelTentacle import ModelTentacle

"""
    Define our overall Octopus model.
    Extends the Component Class.
"""


class ModelOctopus(Component):
    components = None
    contextParent = None
    defaultPos = None

    """
        Init method. Sets up the actual Octopus Object. Makes a call to a helper method, with default Positioning 
        Matrix.
        
        :param parent: Parent Component.
        :type parent: Component.
        :param position: Initial Position.
        :type position: Point.
        :param scale: Scale Matrix.
        :type scale: 1x3 Integer Matrix.
        :param display_obj: Initial Display Object.
        :type display_obj: Component.
        :return: None.
    """

    def __init__(self, parent, position, scale=None, display_obj=None):
        super().__init__(position, display_obj)
        if scale is None:
            self.scale = [0.5, 0.5, 0.5]
        else:
            self.scale = scale

        self.contextParent = parent
        self.components = []

        octopusBody = Component(Point((0, 0, 0)), DisplayableBody(self.contextParent, [0.5, 0.5, 0.5]))
        octopusBody.setDefaultColor(Ct.DARKORANGE2)

        eyes = Component(Point((0, 0, 0)), DisplayableEye(self.contextParent, [0.5, 0.5, 0.5]))

        tentacleOne = ModelTentacle(Point((0, 0, 0)), self.contextParent, 22, self.scale, 1)
        tentacleOne.setDefaultColor(Ct.DARKORANGE3)
        tentacleTwo = ModelTentacle(Point((0, 0, 0)), self.contextParent, -22, self.scale, -1)
        tentacleTwo.setDefaultColor(Ct.DARKORANGE3)

        tentacleThree = ModelTentacle(Point((0, 0, 0)), self.contextParent, 67, self.scale, 1)
        tentacleThree.setDefaultColor(Ct.DARKORANGE3)
        tentacleFour = ModelTentacle(Point((0, 0, 0)), self.contextParent, -67, self.scale, -1)
        tentacleFour.setDefaultColor(Ct.DARKORANGE3)

        tentacleFive = ModelTentacle(Point((0, 0, 0)), self.contextParent, 112, self.scale, 1)
        tentacleFive.setDefaultColor(Ct.DARKORANGE3)
        tentacleSix = ModelTentacle(Point((0, 0, 0)), self.contextParent, -112, self.scale, -1)
        tentacleSix.setDefaultColor(Ct.DARKORANGE3)

        tentacleSeven = ModelTentacle(Point((0, 0, 0)), self.contextParent, 157, self.scale, 1)
        tentacleSeven.setDefaultColor(Ct.DARKORANGE3)
        tentacleEight = ModelTentacle(Point((0, 0, 0)), self.contextParent, -157, self.scale, -1)
        tentacleEight.setDefaultColor(Ct.DARKORANGE3)

        self.addChild(octopusBody)
        octopusBody.addChild(eyes)

        octopusBody.addChild(tentacleOne)
        octopusBody.addChild(tentacleTwo)
        octopusBody.addChild(tentacleThree)
        octopusBody.addChild(tentacleFour)
        octopusBody.addChild(tentacleFive)
        octopusBody.addChild(tentacleSix)
        octopusBody.addChild(tentacleSeven)
        octopusBody.addChild(tentacleEight)

        self.components = [octopusBody, tentacleOne, tentacleTwo, tentacleThree, tentacleFour, tentacleFive,
                           tentacleSix, tentacleSeven, tentacleEight]
        self.tentacles = [tentacleOne, tentacleTwo, tentacleThree, tentacleFour, tentacleFive,
                           tentacleSix, tentacleSeven, tentacleEight]

    """
        Reset to default settings. Overwritten from the original to deal with individual components of the Octopus.

        :param mode: The thing you want to reset.
        :type mode: String.
        :return: None.
    """
    def reset(self, mode="all"):
        for comp in self.components:
            comp.reset(mode)
    """
        Set the Object Position to one of the 5 Pre-Set Positions.
        
        :param positionIndex: Index for the position.
        :type positionIndex: Integer (0-4).
        :return: None.
    """
    def setPosition(self, positionIndex):
        if positionIndex == 0:
            self.setPosZero()
        if positionIndex == 1:
            self.setPosOne()
        if positionIndex == 2:
            self.setPosTwo()
        if positionIndex == 3:
            self.setPosThree()
        if positionIndex == 4:
            self.setPosFour()

    """
        Set the Octopus to Position Zero.
        
        :return: None.
    """
    def setPosZero(self):
        self.reset()
        for tentacle in self.tentacles:
            for i in range(0, 25, 5):
                tentacle.rotate(5, self.uAxis)

    """
        Set the Octopus to Position One.

        :return: None.
    """
    def setPosOne(self):
        self.reset()
        for tentacle in self.tentacles:
            for i in range(0, -55, -5):
                tentacle.rotate(-5, self.uAxis)

    """
        Set the Octopus to Position Two.

        :return: None.
    """
    def setPosTwo(self):
        self.reset()
        self.components[0].rotate(-15, self.uAxis)
        for tentacle in self.tentacles:
            for i in range(0, 20, 5):
                tentacle.rotate(5, self.vAxis)

    """
        Set the Octopus to Position Three.

        :return: None.
    """
    def setPosThree(self):
        self.reset()
        for i in range(0, 25, 5):
            self.tentacles[0].rotate(5, self.uAxis)
            self.tentacles[1].rotate(5, self.uAxis)
            self.tentacles[6].rotate(5, self.uAxis)
            self.tentacles[7].rotate(5, self.uAxis)
        for i in range(0, -25, -5):
            self.tentacles[2].rotate(-5, self.uAxis)
            self.tentacles[3].rotate(-5, self.uAxis)
            self.tentacles[4].rotate(-5, self.uAxis)
            self.tentacles[5].rotate(-5, self.uAxis)

    """
        Set the Octopus to Position Four.

        :return: None.
    """
    def setPosFour(self):
        self.reset()
        self.components[0].rotate(180, self.uAxis)
