"""
    Displayable Torus Class. Sets up the Sphere Vertices, Normals, Color, and EBO Indexes.

    :author: vitor@bu.edu.
    :version: 2021.12.4.
"""
import math

from Displayable import Displayable
from GLBuffer import VAO, VBO, EBO
import numpy as np
import ColorType

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


class DisplayableTorus(Displayable):
    vao = None
    vbo = None
    ebo = None
    shaderProg = None

    vertices = None
    indices = None

    nsides = 0
    rings = 0
    innerRadius = 0
    outerRadius = 0
    color = None

    def __init__(self, shaderProg, innerRadius=0.25, outerRadius=0.5, nsides=36, rings=36, color=ColorType.SOFTBLUE):
        """
            Init the whole Object, calling Helper Methods to build the Structure.

            :return: None.
        """
        super(DisplayableTorus, self).__init__()
        self.shaderProg = shaderProg
        self.shaderProg.use()

        self.vao = VAO()
        self.vbo = VBO()
        self.ebo = EBO()

        self.generate(innerRadius, outerRadius, nsides, rings, color)

    def generate(self, innerRadius=0.25, outerRadius=0.5, nsides=36, rings=36, color=ColorType.SOFTBLUE):
        """
            Generate the Torus Structure, using a Vertex and Index List.

            :return: None.
        """
        self.innerRadius = innerRadius
        self.outerRadius = outerRadius
        self.nsides = nsides
        self.rings = rings
        self.color = color

        self.vertices = np.zeros([6 * (nsides + 1) * (rings + 1), 11])
        self.indices = np.zeros(0)

        vertexCoords = np.zeros([nsides, rings, 3])
        normals = np.zeros([nsides, rings, 3])

        fragOne = (2 * math.pi) / nsides
        fragTwo = (2 * math.pi) / rings
        sigma = - math.pi
        primaryRadius = ((outerRadius - innerRadius) / 2) + innerRadius
        secondaryRadius = (outerRadius - innerRadius) / 2
        for i in range(nsides):
            theta = - math.pi
            for j in range(rings):
                vertexCoords[i, j, :] = [(primaryRadius + secondaryRadius * math.cos(sigma)) * math.cos(theta),
                                         (primaryRadius + secondaryRadius * math.cos(sigma)) * math.sin(theta),
                                         secondaryRadius * math.sin(sigma)]

                nx = np.cross(np.array([- math.sin(theta), math.cos(theta), 0]), np.array(
                    [math.cos(theta) * (-math.sin(sigma)), math.sin(theta) * (-math.sin(sigma)), math.cos(sigma)]))
                normals[i, j, :] = nx / np.linalg.norm(nx)

                theta += fragTwo
            sigma += fragOne

        for i in range(nsides):
            for j in range(rings):
                gridN = i * rings + j

                self.vertices[6 * gridN + 0, 0:3] = vertexCoords[i, j, :]
                self.vertices[6 * gridN + 1, 0:3] = vertexCoords[i, (j + 1) % rings, :]
                self.vertices[6 * gridN + 2, 0:3] = vertexCoords[(i + 1) % nsides, (j + 1) % rings, :]

                self.vertices[6 * gridN + 3, 0:3] = vertexCoords[i, j, :]
                self.vertices[6 * gridN + 4, 0:3] = vertexCoords[(i + 1) % nsides, (j + 1) % rings, :]
                self.vertices[6 * gridN + 5, 0:3] = vertexCoords[(i + 1) % nsides, j, :]

                self.vertices[6 * gridN + 0, 3:6] = normals[i, j, :]
                self.vertices[6 * gridN + 1, 3:6] = normals[i, (j + 1) % rings, :]
                self.vertices[6 * gridN + 2, 3:6] = normals[(i + 1) % nsides, (j + 1) % rings, :]

                self.vertices[6 * gridN + 3, 3:6] = normals[i, j, :]
                self.vertices[6 * gridN + 4, 3:6] = normals[(i + 1) % nsides, (j + 1) % rings, :]
                self.vertices[6 * gridN + 5, 3:6] = normals[(i + 1) % nsides, j, :]

                self.vertices[6 * gridN + 0: 6 * gridN + 6, 6:9] = [*color]
                self.vertices[6 * gridN + 2, 6:9] = [*ColorType.SOFTRED]
                self.vertices[6 * gridN + 5, 6:9] = [*ColorType.SOFTRED]

    def draw(self):
        """
            Draw the actual Sphere.

            :return: None.
        """
        self.vao.bind()
        self.vbo.draw()
        self.vao.unbind()

    def initialize(self):
        """
            Initialize the VBO/EBO Settings.

            :return: None.
        """
        self.vao.bind()
        self.vbo.setBuffer(self.vertices, 11)
        self.ebo.setBuffer(self.indices)

        self.vbo.setAttribPointer(self.shaderProg.getAttribLocation("vertexPos"),
                                  stride=11, offset=0, attribSize=3)
        self.vbo.setAttribPointer(self.shaderProg.getAttribLocation("vertexNormal"),
                                  stride=11, offset=3, attribSize=3)
        self.vbo.setAttribPointer(self.shaderProg.getAttribLocation("vertexColor"),
                                  stride=11, offset=6, attribSize=3)
        self.vao.unbind()
