"""
    All creatures should be added to Vivarium. Some help functions to add/remove creature are defined here.
    Created on 20181028

    :author: micou(Zezhou Sun), vitor@bu.edu
    :version: 2021.1.1
"""
import random

from Point import Point
from Component import Component
from Animation import Animation
from ModelTank import Tank
from ModelShark import Shark
from ModelFish import Fish
from EnvironmentObject import EnvironmentObject

"""
    The Vivarium for our animation
"""
class Vivarium(Component, Animation):
    components = None
    parent = None
    tank = None
    tank_dimensions = None

    def __init__(self, parent):
        self.parent = parent

        self.tank_dimensions = [4, 4, 4]
        tank = Tank(parent, self.tank_dimensions)
        super(Vivarium, self).__init__(Point((0, 0, 0)))

        # Build relationship
        self.addChild(tank)
        self.tank = tank

        # Store all components in one list, for us to access them later
        self.components = [tank]

        # Initialize all Objects inside the Tank
        self.initializeObjs()

    """
        Helper Method to add food objects.
    """
    def initializeObjs(self):
        self.addNewObjInTank(Shark(self.parent, Point((0, 0, 0)), [-0.01, -0.01, 0.00]))
        for i in range(0, random.randint(5, 10)):
            velocity = [0.00, 0.00, 0.00]
            for i in range(0, 3):
                rand = random.random()
                if rand < 0.33:
                    velocity[i] = -0.01
                elif rand < 0.66:
                    velocity[i] = 0.01

            self.addNewObjInTank(Fish(self.parent, Point((0, 0, 0)), velocity))

        # Random position assignment for each of the objects
        for i, comp in enumerate(self.components):
            if comp == self.tank:
                continue
            valOne = random.random() - random.random() + 0.1 * i
            valTwo = random.random() - random.random() + 0.1 * i
            valThree = random.random() - random.random() + 0.1 * i
            comp.setCurrentPosition(Point((valOne, valTwo, valThree)))

    """
        Update all creatures in vivarium. Prompts each creature to update itself, and then removes any creatures that 
        have collided. 
    """
    def animationUpdate(self):
        toDelete = []

        for c in self.components[::-1]:
            if isinstance(c, Animation):
                toDelete += c.animationUpdate()

        for obj in toDelete:
            if obj in self.tank.children:
                self.delObjInTank(obj)

        self.update()

    """
        Helper Method to remove an object from the Tank.
    """
    def delObjInTank(self, obj):
        if isinstance(obj, Component):
            self.tank.children.remove(obj)
            self.components.remove(obj)
            del obj

    """
        Helper Method to add a new object to the Tank.
    """
    def addNewObjInTank(self, newComponent):
        if isinstance(newComponent, Component):
            self.tank.addChild(newComponent)
            self.components.append(newComponent)
        if isinstance(newComponent, EnvironmentObject):
            newComponent.env_obj_list = self.components
