"""
:Student Name: Vitor Manuel Barros de Moura Vicente
:Course: CS 480
:Assignment Number: PA 1
:Due Date: 09/21
:Collaborators: None

This class might seem kind of useless, but I could not keep the DrawPoint Static Method inside Sketch.py without
having a circular dependency, so I decided to move the method to its own class. So it serves simply as a Object Class
to draw points.
"""

from Buff import Buff
from Point import Point
from ColorType import ColorType


class PointMaker:
    @staticmethod
    def drawPoint(buff, point):
        """
        Draw a point on buff

        :param buff: The buff to draw point on
        :type buff: Buff
        :param point: A point to draw on buff
        :type point: Point
        :rtype: None
        """
        x, y = point.coords
        c = point.color
        # because we have already specified buff.buff has data type uint8, type conversion will be done in numpy
        buff.buff[x, y, 0] = c.r * 255
        buff.buff[x, y, 1] = c.g * 255
        buff.buff[x, y, 2] = c.b * 255
