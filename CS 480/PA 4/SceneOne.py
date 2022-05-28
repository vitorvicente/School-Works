"""
    Define a Fixed Scene with 2 Objects and 3 Lights.
    Uses the Cube, and Torus Objects. Lights rotate around them.
    Each object has a different Material.

    :author: vitor@bu.edu.
    :version: 2021.12.4.
"""

import math

import numpy as np

import ColorType
from Animation import Animation
from Component import Component
from Light import Light
from Material import Material, MATERIALONE, MATERIALTWO
from Point import Point
import GLUtility

from DisplayableCube import DisplayableCube
from DisplayableTorus import DisplayableTorus


class SceneOne(Component, Animation):
    shaderProg = None
    glutility = None

    lights = None
    lightCubes = None
    allLights = None
    allLightCubes = None

    lRadius = None
    lAngles = None
    lTransformations = None

    def __init__(self, shaderProg):
        """
            Init the whole Scene, calling Helper Methods to build the Objects and Lights.
        """
        super().__init__(Point((0, 0, 0)))
        self.shaderProg = shaderProg
        self.glutility = GLUtility.GLUtility()

        self.initTransformations()
        self.initObjects()
        self.initLights()

    def lightPos(self, radius, thetaAng, transformationMatrix):
        """
            Get the current Position of the Lights.

            :return: None.
        """
        r = np.zeros(4)
        r[0] = radius * math.cos(thetaAng / 180 * math.pi)
        r[2] = radius * math.sin(thetaAng / 180 * math.pi)
        r[3] = 1
        r = transformationMatrix @ r
        return r[0:3]

    def animationUpdate(self):
        """
            Update the current Positions of the Lights.

            :return: None.
        """
        self.lAngles[0] = (self.lAngles[0] + 0.5) % 360
        self.lAngles[1] = (self.lAngles[1] + 0.7) % 360
        self.lAngles[2] = (self.lAngles[2] + 1.0) % 360
        for i, v in enumerate(self.lights):
            lPos = self.lightPos(self.lRadius, self.lAngles[i], self.lTransformations[i])
            self.lightCubes[i].setCurrentPosition(Point(lPos))
            self.lights[i].setPosition(lPos)
            self.shaderProg.setLight(i, v)

        for c in self.children:
            if isinstance(c, Animation):
                c.animationUpdate()

    def initialize(self):
        """
            Initialize the Shaders Engine.

            :return: None.
        """
        self.shaderProg.clearAllLights()
        for i, v in enumerate(self.lights):
            self.shaderProg.setLight(i, v)
        super().initialize()

    def initTransformations(self):
        """
            Initialize the required Data for the Light Transformations.

            :return: None.
        """
        self.lTransformations = [self.glutility.translate(0, 2, 0, False),
                                 self.glutility.rotate(60, [0, 0, 1], False),
                                 self.glutility.rotate(120, [0, 0, 1], False)]
        self.lRadius = 3
        self.lAngles = [0, 0, 0]

    def initObjects(self):
        """
            Build the Scene Objects, making use of the Displayable Classes built elsewhere.
            Each Object has a unique placement, and Material. All objects begin rendering with the Lighting Engine.

            :return: None.
        """
        cube = Component(Point((-1, 0, 0)), DisplayableCube(self.shaderProg, 1.5, 1, 1.5))
        m1 = Material(np.array((0.1, 0.1, 0.1, 0.1)), np.array((0.2, 0.2, 0.2, 1)),
                      np.array((0.4, 0.4, 0.4, 0.1)), 64)
        cube.setMaterial(m1)
        cube.renderingRouting = "lighting"
        self.addChild(cube)

        torus = Component(Point((1, 0, 0)), DisplayableTorus(self.shaderProg, 0.25, 0.5, 36, 36))
        m2 = Material(np.array((0.1, 0.1, 0.1, 0.1)), np.array((0.2, 0.2, 0.2, 1)),
                      np.array((0, 0, 0, 1.0)), 64)
        torus.setMaterial(m2)
        torus.renderingRouting = "lighting"
        torus.rotate(90, torus.uAxis)
        self.addChild(torus)

    def initLights(self):
        """
            Build the Scene Lights, making use of the Light Class, and of DisplayableCubes to represent them.
            Each Light has a unique placement.

            :return: None.
        """
        l0 = Light(self.lightPos(self.lRadius, self.lAngles[0], self.lTransformations[0]),
                   np.array((*ColorType.SOFTRED, 1.0)))
        lightCube0 = Component(Point((0, 0, 0)), DisplayableCube(self.shaderProg, 0.1, 0.1, 0.1, ColorType.SOFTRED))
        lightCube0.renderingRouting = "vertex"
        l1 = Light(self.lightPos(self.lRadius, self.lAngles[1], self.lTransformations[1]),
                   np.array((*ColorType.SOFTBLUE, 1.0)))
        lightCube1 = Component(Point((0, 0, 0)), DisplayableCube(self.shaderProg, 0.1, 0.1, 0.1, ColorType.SOFTBLUE))
        lightCube1.renderingRouting = "vertex"
        l2 = Light(self.lightPos(self.lRadius, self.lAngles[2], self.lTransformations[2]),
                   np.array((*ColorType.SOFTGREEN, 1.0)))
        lightCube2 = Component(Point((0, 0, 0)), DisplayableCube(self.shaderProg, 0.1, 0.1, 0.1, ColorType.SOFTGREEN))
        lightCube2.renderingRouting = "vertex"

        self.addChild(lightCube0)
        self.addChild(lightCube1)
        self.addChild(lightCube2)
        self.lights = [l0, l1, l2]
        self.lightCubes = [lightCube0, lightCube1, lightCube2]
        self.allLights = [l0, l1, l2]
        self.allLightCubes = [lightCube0, lightCube1, lightCube2]
