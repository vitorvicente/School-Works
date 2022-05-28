"""
    Define a class to store Material information.
    First version in 11/08/2021. Last version Created on 12/06/2021

    :author: micou(Zezhou Sun), vitor@bu.edu.
    :version: 2021.1.1
"""

import numpy as np


class Material:
    ambient = None
    diffuse = None
    specular = None
    highLight = None

    def __init__(self, ambient=None, diffuse=None, specular=None, highlight=32):
        if ambient is not None:
            self.setAmbient(ambient)
        else:
            self.ambient = np.array((0, 0, 0, 0))

        if diffuse is not None:
            self.setDiffuse(diffuse)
        else:
            self.diffuse = np.array((0, 0, 0, 0))

        if specular is not None:
            self.setSpecular(specular)
        else:
            self.specular = np.array((0, 0, 0, 0))
        self.highLight = highlight

    def setAmbient(self, ambient):
        if (not isinstance(ambient, np.ndarray)) or ambient.size != 4:
            raise TypeError("ambient must be a size 4 ndarray")
        self.ambient = ambient

    def setDiffuse(self, diffuse):
        if (not isinstance(diffuse, np.ndarray)) or diffuse.size != 4:
            raise TypeError("diffuse must be a size 4 ndarray")
        self.diffuse = diffuse

    def setSpecular(self, specular):
        if (not isinstance(specular, np.ndarray)) or specular.size != 4:
            raise TypeError("specular must be a size 4 ndarray")
        self.specular = specular

    def setHighlight(self, highLight):
        if type(highLight) != (int or float):
            print(type(highLight))
            raise TypeError("highLight must be a float/int")
        self.highLight = highLight

    def setMaterial(self, ambient, diffuse, specular, highlight):
        self.setAmbient(ambient)
        self.setDiffuse(diffuse)
        self.setSpecular(specular)
        self.setHighlight(highlight)


"""
    Pre-Set Materials to be used on the Project.
"""
MATERIALONE = Material(np.array((0.1, 0.1, 0.1, 0.1)), np.array((0.2, 0.2, 0.2, 1)),
                       np.array((0.4, 0.4, 0.4, 0.1)), 32)
MATERIALTWO = Material(np.array((0.2, 0.2, 0.2, 0.2)), np.array((0.3, 0.3, 0.3, 1)),
                       np.array((0.5, 0.5, 0.5, 0.1)), 32)
MATERIALTHREE = Material(np.array((0.3, 0.3, 0.3, 0.3)), np.array((0.4, 0.4, 0.4, 1)),
                         np.array((0.1, 0.1, 0.1, 0.1)), 32)
MATERIALFOUR = Material(np.array((0.4, 0.4, 0.4, 0.4)), np.array((0.5, 0.5, 0.5, 1)),
                        np.array((0.7, 0.7, 0.7, 0.1)), 32)
