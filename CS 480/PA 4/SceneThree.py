"""
    Define a Fixed Scene with 3 Objects and 2 Lights.
    Uses the Ellipsoid, Sphere, and Cylinder Objects.
    Each object has a different Material.

    :author: vitor@bu.edu.
    :version: 2021.12.4.
"""

import numpy as np

import ColorType
from Component import Component
from DisplayableCylinder import DisplayableCylinder
from DisplayableEllipsoid import DisplayableEllipsoid
from DisplayableSphere import DisplayableSphere
from Light import Light
from Material import Material, MATERIALONE, MATERIALTWO, MATERIALTHREE
from Point import Point
import GLUtility

from DisplayableCube import DisplayableCube


class SceneThree(Component):
    shaderProg = None
    glutility = None

    lights = None
    lightCubes = None
    allLights = None
    allLightCubes = None

    def __init__(self, shaderProg):
        """
            Init the whole Scene, calling Helper Methods to build the Objects and Lights.

            :return: None.
        """
        super().__init__(Point((0, 0, 0)))
        self.shaderProg = shaderProg
        self.glutility = GLUtility.GLUtility()

        self.initObjects()
        self.initLights()

    def initialize(self):
        """
            Initialize the Shaders Engine.

            :return: None.
        """
        self.shaderProg.clearAllLights()
        for i, v in enumerate(self.lights):
            self.shaderProg.setLight(i, v)
        super().initialize()

    def initObjects(self):
        """
            Build the Scene Objects, making use of the Displayable Classes built elsewhere.
            Each Object has a unique placement, and Material. All objects begin rendering with the Lighting Engine.

            :return: None.
        """
        ellipsoid = Component(Point((0, -2, 0)),
                              DisplayableEllipsoid(self.shaderProg, 1, 1.5, 2, 18, 36, ColorType.SOFTRED))
        ellipsoid.setMaterial(MATERIALONE)
        ellipsoid.renderingRouting = "lighting"
        self.addChild(ellipsoid)

        sphere = Component(Point((2, 2, 0)), DisplayableSphere(self.shaderProg, 1, 18, 36, ColorType.SOFTBLUE))
        sphere.setMaterial(MATERIALTWO)
        sphere.renderingRouting = "lighting"
        self.addChild(sphere)

        cylinder = Component(Point((-2, 2, 0)),
                             DisplayableCylinder(self.shaderProg, 0.5, 1, 24, 48, ColorType.SOFTGREEN))
        cylinder.setMaterial(MATERIALTHREE)
        cylinder.renderingRouting = "lighting"
        self.addChild(cylinder)

    def initLights(self):
        """
            Build the Scene Lights, making use of the Light Class, and of DisplayableCubes to represent them.
            Each Light has a unique placement.

            :return: None.
        """
        l0 = Light(Point([0.0, 3.0, 3.0]),
                   np.array((*ColorType.GREEN, 1.0)))
        lightCube0 = Component(Point((0.0, 3.0, 3.0)), DisplayableCube(self.shaderProg, 0.1, 0.1, 0.1, ColorType.GREEN))
        lightCube0.renderingRouting = "vertex"

        l1 = Light(Point([0.0, -3.0, -3.0]),
                   np.array((*ColorType.PINK, 1.0)), np.array((0, -1, -1)))
        lightCube1 = Component(Point((0.0, -3.0, -3.0)),
                               DisplayableCube(self.shaderProg, 0.1, 0.1, 0.1, ColorType.PINK))
        lightCube1.renderingRouting = "vertex"

        l2 = Light(Point([3.0, 0.0, -3.0]),
                   np.array((*ColorType.GREENYELLOW, 1.0)), None, np.array((-1, 0, 1)), np.array((0.1, 0.1, 0.1)),
                   float(0.01))
        lightCube2 = Component(Point((3.0, 0.0, -3.0)),
                               DisplayableCube(self.shaderProg, 0.1, 0.1, 0.1, ColorType.GREENYELLOW))
        lightCube2.renderingRouting = "vertex"

        self.addChild(lightCube0)
        self.addChild(lightCube1)
        self.addChild(lightCube2)
        self.lights = [l0, l1, l2]
        self.lightCubes = [lightCube0, lightCube1, lightCube2]
        self.allLights = [l0, l1, l2]
        self.allLightCubes = [lightCube0, lightCube1, lightCube2]