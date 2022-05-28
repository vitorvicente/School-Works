"""
    Displayable Cylinder Class. Sets up the Cylinder Vertices, Normals, Color, and EBO Indexes.

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


class DisplayableCylinder(Displayable):
    vao = None
    vbo = None
    ebo = None
    shaderProg = None

    vertices = None
    indices = None

    endRadius = None
    height = None
    slices = None
    stacks = None
    color = None

    def __init__(self, shaderProg, endRadius=0.5, height=1, slices=10, stacks=30, color=ColorType.BLUE):
        """
            Init the whole Object, calling Helper Methods to build the Structure.

            :return: None.
        """
        super(DisplayableCylinder, self).__init__()
        self.shaderProg = shaderProg
        self.shaderProg.use()

        self.vao = VAO()
        self.vbo = VBO()
        self.ebo = EBO()

        self.generate(endRadius, height, slices, stacks, color)

    def generate(self, endRadius=0.5, height=1, slices=5, stacks=10, color=ColorType.BLUE):
        """
            Generate the Cylinder Structure, using a Vertex and Index List.

            :return: None.
        """
        self.endRadius = endRadius
        self.height = height
        self.slices = slices
        self.stacks = stacks
        self.color = color

        self.vertices = np.zeros([((6 * slices * (stacks - 1)) + (2 * stacks * 3)), 11])
        self.indices = np.zeros(0)

        vertexCoords = np.zeros([slices + 1, stacks, 3])
        normals = np.zeros([slices + 1, stacks, 3])

        heightFrag = height / slices
        sideFrag = (4 * math.pi) / stacks
        curHeight = -height / 2
        for i in range(slices):
            theta = -math.pi
            for j in range(stacks):
                vertexCoords[i, j, :] = [endRadius * math.cos(theta),
                                         curHeight,
                                         endRadius * math.sin(theta)]
                normals[i, j, :] = [math.cos(theta),
                                    0,
                                    math.sin(theta)]
                theta += sideFrag
            curHeight += heightFrag

        for i in range(slices):
            for j in range(stacks - 1):
                gridN = i * stacks + j

                self.vertices[6 * gridN + 0, 0:3] = vertexCoords[i, j, :]
                self.vertices[6 * gridN + 1, 0:3] = vertexCoords[i, (j + 1) % stacks, :]
                self.vertices[6 * gridN + 2, 0:3] = vertexCoords[i + 1, (j + 1) % stacks, :]

                self.vertices[6 * gridN + 3, 0:3] = vertexCoords[i, j, :]
                self.vertices[6 * gridN + 4, 0:3] = vertexCoords[i + 1, (j + 1) % stacks, :]
                self.vertices[6 * gridN + 5, 0:3] = vertexCoords[i + 1, j, :]

                self.vertices[6 * gridN + 0, 3:6] = normals[i, j, :]
                self.vertices[6 * gridN + 1, 3:6] = normals[i, (j + 1) % stacks, :]
                self.vertices[6 * gridN + 2, 3:6] = normals[i + 1, (j + 1) % stacks, :]

                self.vertices[6 * gridN + 3, 3:6] = normals[i, j, :]
                self.vertices[6 * gridN + 4, 3:6] = normals[i + 1, (j + 1) % stacks, :]
                self.vertices[6 * gridN + 5, 3:6] = normals[i + 1, j, :]

                self.vertices[6 * gridN + 0: 6 * gridN + 6, 6:9] = [*color]

        gridVal = (6 * slices * (stacks - 1))
        for j in range(stacks):
            self.vertices[gridVal + 0, 0:3] = [0, -height / 2, 0]
            self.vertices[gridVal + 1, 0:3] = vertexCoords[0, j, :]
            self.vertices[gridVal + 2, 0:3] = vertexCoords[0, (j + 1) % stacks, :]
            self.vertices[gridVal + 3, 0:3] = [0, height / 2, 0]
            self.vertices[gridVal + 4, 0:3] = vertexCoords[slices - 1, j, :]
            self.vertices[gridVal + 5, 0:3] = vertexCoords[slices - 1, (j + 1) % stacks, :]

            self.vertices[gridVal + 0, 3:6] = [0, 1, 0]
            self.vertices[gridVal + 1, 3:6] = [0, 1, 0]
            self.vertices[gridVal + 2, 3:6] = [0, 1, 0]
            self.vertices[gridVal + 3, 3:6] = [0, -1, 0]
            self.vertices[gridVal + 4, 3:6] = [0, -1, 0]
            self.vertices[gridVal + 5, 3:6] = [0, -1, 0]

            self.vertices[gridVal + 0: gridVal + 6, 6:9] = [*color]
            gridVal += 6

    def draw(self):
        """
            Draw the actual Cylinder.

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
