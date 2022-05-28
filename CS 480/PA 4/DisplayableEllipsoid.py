"""
    Displayable Ellipsoid Class. Sets up the Ellipsoid Vertices, Normals, Color, and EBO Indexes.

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


class DisplayableEllipsoid(Displayable):
    vao = None
    vbo = None
    ebo = None
    shaderProg = None

    vertices = None
    indices = None

    radiusInX = None
    radiusInY = None
    radiusInZ = None
    slices = None
    stacks = None
    color = None

    def __init__(self, shaderProg, radiusInX=0.5, radiusInY=1, radiusInZ=1.5, stacks=18, slices=36,
                 color=ColorType.BLUE):
        """
            Init the whole Object, calling Helper Methods to build the Structure.

            :return: None.
        """
        super(DisplayableEllipsoid, self).__init__()
        self.shaderProg = shaderProg
        self.shaderProg.use()

        self.vao = VAO()
        self.vbo = VBO()
        self.ebo = EBO()

        self.generate(radiusInX, radiusInY, radiusInZ, stacks, slices, color)

    def generate(self, radiusInX=0.5, radiusInY=1, radiusInZ=1.5, stacks=18, slices=36, color=ColorType.BLUE):
        """
            Generate the Ellipsoid Structure, using a Vertex and Index List.

            :return: None.
        """
        self.radiusInX = radiusInX
        self.radiusInY = radiusInY
        self.radiusInZ = radiusInZ
        self.slices = slices
        self.stacks = stacks
        self.color = color

        self.vertices = np.zeros([(6 * slices * (stacks - 1)), 11])
        self.indices = np.zeros(0)

        vertexCoords = np.zeros([stacks, slices, 3])
        normals = np.zeros([stacks, slices, 3])
        for i in range(stacks):
            phi = i / (stacks - 1) * math.pi - math.pi / 2
            for j in range(slices):
                theta = j / slices * 2 * math.pi
                vertexCoords[i, j, :] = [self.radiusInX * math.sin(theta) * math.cos(phi),
                                         self.radiusInY * math.sin(theta) * math.sin(phi),
                                         self.radiusInZ * math.cos(theta)]
                normals[i, j, :] = [math.sin(theta) * math.cos(phi),
                                    math.sin(theta) * math.sin(phi),
                                    math.cos(theta)]

        for i in range(stacks - 1):
            for j in range(slices):
                gridN = i * slices + j

                self.vertices[6 * gridN + 0, 0:3] = vertexCoords[i, j, :]
                self.vertices[6 * gridN + 1, 0:3] = vertexCoords[i, (j + 1) % slices, :]
                self.vertices[6 * gridN + 2, 0:3] = vertexCoords[i + 1, (j + 1) % slices, :]

                self.vertices[6 * gridN + 3, 0:3] = vertexCoords[i, j, :]
                self.vertices[6 * gridN + 4, 0:3] = vertexCoords[i + 1, (j + 1) % slices, :]
                self.vertices[6 * gridN + 5, 0:3] = vertexCoords[i + 1, j, :]

                self.vertices[6 * gridN + 0, 3:6] = normals[i, j, :]
                self.vertices[6 * gridN + 1, 3:6] = normals[i, (j + 1) % slices, :]
                self.vertices[6 * gridN + 2, 3:6] = normals[i + 1, (j + 1) % slices, :]

                self.vertices[6 * gridN + 3, 3:6] = normals[i, j, :]
                self.vertices[6 * gridN + 4, 3:6] = normals[i + 1, (j + 1) % slices, :]
                self.vertices[6 * gridN + 5, 3:6] = normals[i + 1, j, :]

                self.vertices[6 * gridN + 0: 6 * gridN + 6, 6:9] = [*color]

    def draw(self):
        """
            Draw the actual Ellipsoid.

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
