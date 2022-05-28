"""
:Student Name: Vitor Manuel Barros de Moura Vicente
:Course: CS 480
:Assignment Number: PA 1
:Due Date: 09/21
:Collaborators: None

This class serves as a Dynamic Object used to Draw Lines by the Sketch.py file. It uses Bresenham's Algorithm to draw
the lines. It includes a main entry method, alongside other helper Drawing Methods, as well as some Static Helper
Functions.
"""

import random

from Buff import Buff
from Point import Point
from ColorType import ColorType
from PointMaker import PointMaker


class LineMaker:
    """
        Main entry point to drawing Lines. It calculates the slope of the line, and passes
        the actual work off to each specific usecase method.

        :param buff: The buff to edit
        :type buff: Buff
        :param p1: One end point of the line
        :type p1: Point
        :param p2: Another end point of the line
        :type p2: Point
        :param doSmooth: Control flag of color smooth interpolation
        :type doSmooth: bool
        :param doAA: Control flag of doing anti-aliasing
        :type doAA: bool
        :param doAAlevel: anti-aliasing super sampling level
        :type doAAlevel: int
        :rtype: List<Point>
    """

    def drawLine(self, buff, p1, p2, doSmooth=True, doAA=False, doAAlevel=4):
        # Pre-Preparation
        points = []

        # Getting Base Point Coords
        p1_coords = p1.getCoords()
        p2_coords = p2.getCoords()

        if p1_coords[1] == p2_coords[1] or p1_coords[0] == p2_coords[0]:
            points = self.drawHorizontalOrVerticalLine(buff, p1, p2, doSmooth, doAA, doAAlevel)
            return points

        m = (p2_coords[1] - p1_coords[1]) / (p2_coords[0] - p1_coords[0])

        if -1 <= m <= 1:
            points = self.drawLineZeroToOne(buff, p1, p2, doSmooth, doAA, doAAlevel)
        else:
            points = self.drawLineOneToInf(buff, p1, p2, doSmooth, doAA, doAAlevel)

        return points

    """
        This method deals with the special case in Bresenham's Algorithm where the slope is either 0, or infinity. 
        This means that the desired line is either Horizontal, or Vertical.

        :param buff: The buff to edit.
        :type buff: Buff.
        :param p1: One end point of the line.
        :type p1: Point.
        :param p2: Another end point of the line.
        :type p2: Point.
        :param doSmooth: Control flag of color smooth interpolation.
        :type doSmooth: bool.
        :param doAA: Control flag of doing anti-aliasing.
        :type doAA: bool.
        :param doAAlevel: anti-aliasing super sampling level.
        :type doAAlevel: int.
        :rtype: List<Point>.
    """

    def drawHorizontalOrVerticalLine(self, buff, p1, p2, doSmooth=True, doAA=False, doAAlevel=4):
        # Make sure p1 is to the left of p2, if not, switch
        p1, p2 = self.checkAndInvertPoints(p1, p2)

        # Pre-Preparation
        p1_coords = p1.getCoords()
        p2_coords = p2.getCoords()

        points = []

        prep_color = self.getDefaultColor(p1, p2)

        # Case Switches for Distinct Slopes
        horizontal = p1_coords[1] == p2_coords[1]
        total_steps = p2_coords[0] - p1_coords[0] if horizontal else p2_coords[1] - p1_coords[1] if p1_coords[1] < \
                                                                                                    p2_coords[1] else \
            p1_coords[1] - p2_coords[1]
        boundary_low = p1_coords[0] if horizontal else p1_coords[1] if p1_coords[1] < p2_coords[1] else p2_coords[1]
        boundary_high = p2_coords[0] if horizontal else p2_coords[1] if p1_coords[1] < p2_coords[1] else p1_coords[1]

        # Loop Variables
        pre_coords = p1_coords if horizontal else p1_coords if p1_coords[1] < p2_coords[1] else p2_coords
        cur_step = 1

        # Loop
        for x in range(boundary_low, boundary_high + 1, 1):
            pre_coords = (x, pre_coords[1]) if horizontal else (pre_coords[0], x)
            if doSmooth:
                prep_color = self.getColorSmooth(p1, p2, cur_step, total_steps)
                cur_step += 1

            points.append(Point(pre_coords, prep_color))
            PointMaker.drawPoint(buff, points[-1])

        return points

    """
        This method deals with what is the base case of the Algorithm, drawing lines with slopes between -1 and 1, 
        using Bresenham's Algorithm. It includes some logic to allow for the math to change when the slope is 
        negative instead of positive.

        :param buff: The buff to edit.
        :type buff: Buff.
        :param p1: One end point of the line.
        :type p1: Point.
        :param p2: Another end point of the line.
        :type p2: Point.
        :param doSmooth: Control flag of color smooth interpolation.
        :type doSmooth: bool.
        :param doAA: Control flag of doing anti-aliasing.
        :type doAA: bool.
        :param doAAlevel: anti-aliasing super sampling level.
        :type doAAlevel: int.
        :rtype: List<Point>.
    """

    def drawLineZeroToOne(self, buff, p1, p2, doSmooth=True, doAA=False, doAAlevel=4):
        # Make sure p1 is to the left of p2, if not, switch
        p1, p2 = self.checkAndInvertPoints(p1, p2)

        # Pre-Preparation
        p1_coords = p1.getCoords()
        p2_coords = p2.getCoords()
        delta_x = p2_coords[0] - p1_coords[0]
        delta_y = p2_coords[1] - p1_coords[1]

        points = []

        prep_color = self.getDefaultColor(p1, p2)

        # Case Switches for Distinct Slopes
        m = delta_y / delta_x
        pre_compute_two_sign = -1 if m > 0 else 1
        step_sign = 1 if m > 0 else -1
        base_case = delta_y * 2 - delta_x if m > 0 else delta_x - delta_y * 2

        # Coordinate Pre-Computations
        pre_compute_one = delta_y * 2
        pre_compute_two = pre_compute_one + pre_compute_two_sign * (2 * delta_x)

        # Loop Variables
        pre_val = 0
        pre_coords = p1_coords
        pre_choice = 0
        cur_step = 1
        total_steps = delta_x

        # Loop
        for x in range(p1_coords[0], p2_coords[0], 1):
            if pre_coords[0] == p1_coords[0]:
                pre_val = base_case
            else:
                if pre_choice == 1:
                    pre_val = pre_val + pre_compute_two
                else:
                    pre_val = pre_val + pre_compute_one

            if (m > 0 and pre_val > 0) or (m < 0 and pre_val < 0):
                pre_coords = (x, pre_coords[1] + step_sign)
                pre_choice = 1
            else:
                pre_coords = (x, pre_coords[1])
                pre_choice = 0

            if doSmooth:
                prep_color = self.getColorSmooth(p1, p2, cur_step, total_steps)
                cur_step += 1

            points.append(Point(pre_coords, prep_color))
            PointMaker.drawPoint(buff, Point(pre_coords, prep_color))

        points.append(p2)

        return points

    """
        This method deals with the special case in Bresenham's Algorithm where the slope is either between 1 and 
        Positive Infinity or between -1 and Negative Infinity. It steps through the Y coord instead of the X. It  
        includes some logic to allow for the math to change when the slope is negative instead of positive.

        :param buff: The buff to edit.
        :type buff: Buff.
        :param p1: One end point of the line.
        :type p1: Point.
        :param p2: Another end point of the line.
        :type p2: Point.
        :param doSmooth: Control flag of color smooth interpolation.
        :type doSmooth: bool.
        :param doAA: Control flag of doing anti-aliasing.
        :type doAA: bool.
        :param doAAlevel: anti-aliasing super sampling level.
        :type doAAlevel: int.
        :rtype: List<Point>.
    """

    def drawLineOneToInf(self, buff, p1, p2, doSmooth=True, doAA=False, doAAlevel=4):
        # Make sure p1 is to the left of p2, if not, switch
        p1, p2 = self.checkAndInvertPoints(p1, p2)

        # Pre-Preparation
        p1_coords = p1.getCoords()
        p2_coords = p2.getCoords()
        delta_x = p2_coords[0] - p1_coords[0]
        delta_y = p2_coords[1] - p1_coords[1]

        points = []

        prep_color = self.getDefaultColor(p1, p2)

        # Case Switches for Distinct Slopes
        m = delta_y / delta_x
        pre_compute_one_sign = -1 if m > 0 else 1
        val_calc_sign = 1 if m > 0 else -1
        loop_step_sign = 1 if m > 0 else -1

        # Coordinate Pre-Computations
        pre_compute_one = pre_compute_one_sign * 2 * delta_x
        pre_compute_two = pre_compute_one + (2 * delta_y)

        # Loop Variables
        pre_val = 0
        pre_coords = p1_coords
        pre_choice = 0
        cur_step = 1
        total_steps = abs(delta_y)

        # Loop
        for x in range(p1_coords[1], p2_coords[1], loop_step_sign):
            if pre_coords[1] == p1_coords[1]:
                pre_val = delta_y + pre_compute_one
            else:
                if pre_choice == 1:
                    pre_val = pre_val + val_calc_sign * pre_compute_two
                else:
                    pre_val = pre_val + val_calc_sign * pre_compute_one

            if pre_val > 0:
                pre_coords = (pre_coords[0], x)
                pre_choice = 0
            else:
                pre_coords = (pre_coords[0] + 1, x)
                pre_choice = 1

            if doSmooth:
                prep_color = self.getColorSmooth(p1, p2, cur_step, total_steps)
                cur_step += 1

            points.append(Point(pre_coords, prep_color))
            PointMaker.drawPoint(buff, Point(pre_coords, prep_color))

        points.append(p2)

        return points

    """
        Static Helper Function for the DrawLine Methods, checks the relative position of the two input points, 
        and assures that p1 is at the left of p2, if not it switches and returns the correct order.

        :param p1: One end point of the line.
        :type p1: Point.
        :param p2: The other end point of the line.
        :type p2: Point.
        :rtype: Tuple(Point, Point).
    """

    @staticmethod
    def checkAndInvertPoints(p1, p2):
        if p1.getCoords()[0] > p2.getCoords()[0]:
            p1, p2 = p2, p1

        return p1, p2

    """
        Static Helper Function for the DrawLine Methods, returns the Default Color of the line for when Smoothing is 
        not enabled. This color is picked randomly between the color of the two points.

        :param p1: One end point of the line.
        :type p1: Point.
        :param p2: The other end point of the line.
        :type p2: Point.
        :rtype: ColorType.
    """

    @staticmethod
    def getDefaultColor(p1, p2):
        return p1.getColor() if random.random() < 0.5 else p2.getColor()

    """
        Static Helper Function for the DrawLine Methods, returns the interpolated Color for a specific point on the 
        Line. Uses the Interpolation Equation seen in class, using the current step in regards to the total steps in 
        the drawing of the line, as a t.

        :param p1: One end point of the line.
        :type p1: Point.
        :param p2: The other end point of the line.
        :type p2: Point.
        :param cur_step: Current Drawing Loop Step.
        :type cur_step: int.
        :param total_steps: Total Drawing Loop Steps.
        :type cur_step: int.
        :rtype: ColorType.
    """

    @staticmethod
    def getColorSmooth(p1, p2, cur_step, total_steps):
        p1_color = p1.getColor().getRGB()
        p2_color = p2.getColor().getRGB()

        t = (cur_step / total_steps)
        r = (1 - t) * p1_color[0] + t * p2_color[0]
        g = (1 - t) * p1_color[1] + t * p2_color[1]
        b = (1 - t) * p1_color[2] + t * p2_color[2]
        prep_color = ColorType(r, g, b)

        return prep_color
