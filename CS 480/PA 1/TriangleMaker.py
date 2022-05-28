"""
:Student Name: Vitor Manuel Barros de Moura Vicente
:Course: CS 480
:Assignment Number: PA 1
:Due Date: 09/21
:Collaborators: None

This class serves as a Dynamic Object used to Draw Truabgkes by the Sketch.py file. It calls its own lineMaker
Object to draw lines. It includes a main entry method, alongside other helper Drawing Methods, as well as
some Static Helper Functions.
"""

from Buff import Buff
from LineMaker import LineMaker
from Point import Point
from ColorType import ColorType
from CanvasBase import CanvasBase
from PointMaker import PointMaker


class TriangleMaker:

    lineMaker = None

    def __init__(self, lineMaker):
        self.lineMaker = lineMaker

    """
        Main Drawing Method, draws a Triangle on the given Buffer from the Points given

        :param buff: The buff to edit
        :type buff: Buff
        :param p1: First triangle vertex
        :param p2: Second triangle vertex
        :param p3: Third triangle vertex
        :type p1: Point
        :type p2: Point
        :type p3: Point
        :param doSmooth: Color smooth filling control flag
        :type doSmooth: bool
        :param doAA: Anti-aliasing control flag
        :type doAA: bool
        :param doAAlevel: Anti-aliasing super sampling level
        :type doAAlevel: int
        :param doTexture: Draw triangle with texture control flag
        :type doTexture: bool
        :rtype: None
    """
    def drawTriangle(self, buff, p1, p2, p3, doSmooth=True, doAA=False, doAAlevel=4, doTexture=False):
        # Smoothing Handling
        if not doSmooth:
            p2.setColor(p1.getColor())
            p3.setColor(p1.getColor())

        # Call Helper Method to Confirm Shape
        confirm_shape, interest_one, interest_two, interest_three = self.confirmTriangleShape(p1, p2, p3)

        # Deal with Different Triangle Shapes
        if confirm_shape == "point":
            PointMaker.drawPoint(buff, interest_one)
        elif confirm_shape == "line":
            self.lineMaker.drawLine(buff, interest_one, interest_two, doSmooth, doAA, doAAlevel)
        elif confirm_shape == "lower_triangle":
            self.drawLowerTriangle(buff, interest_one, interest_two, interest_three, doSmooth, doAA, doAAlevel,
                                   doTexture)
        elif confirm_shape == "upper_triangle":
            self.drawUpperTriangle(buff, interest_one, interest_two, interest_three, doSmooth, doAA, doAAlevel,
                                   doTexture)
        elif confirm_shape == "complex_triangle":
            self.drawComplexTriangle(buff, interest_three, interest_two, interest_one, doSmooth, doAA, doAAlevel,
                                       doTexture)

        return

    """
        Deal with the scenario where the given input is a complex Triangle, and needs breaking down into an Upper 
        Triangle and a Lower Triangle..

        :param buff: The buff to edit.
        :type buff: Buff.
        :param p1: Upper-Most Vertex by Y.
        :param p2: Second Upper-Most Vertex by Y.
        :param p3: Lower-Most Vertex by Y.
        :type p1: Point.
        :type p2: Point.
        :type p3: Point.
        :param doSmooth: Color smooth filling control flag.
        :type doSmooth: bool.
        :param doAA: Anti-aliasing control flag.
        :type doAA: bool.
        :param doAAlevel: Anti-aliasing super sampling level.
        :type doAAlevel: int.
        :param doTexture: Draw triangle with texture control flag.
        :type doTexture: bool.
        :rtype: None.
    """

    def drawComplexTriangle(self, buff, p1, p2, p3, doSmooth=True, doAA=False, doAAlevel=4, doTexture=False):
        # Drawing the Longer Edge, to find fourth Helper Point
        longer_edge_points = self.lineMaker.drawLine(buff, p1, p3, doSmooth, doAA, doAAlevel)
        sorted_longer_edge = self.sortPoints(longer_edge_points, p1, p3)

        # Finding fourth Helper Point, and re-shuffling to assure correct left-right positioning
        p4 = sorted_longer_edge[p2.getCoords()[1]]
        p2, p4 = LineMaker.checkAndInvertPoints(p2, p4)

        # Drawing the two triangles
        self.drawUpperTriangle(buff, p1, p2, p4, doSmooth, doAA, doAAlevel, doTexture)
        self.drawLowerTriangle(buff, p3, p2, p4, doSmooth, doAA, doAAlevel, doTexture)

        return

    """
        Draw the upper part of a compound Triangle. Calls the DrawLine function to do the heavy lifting, as well as 
        the SortPoints function to assist in getting the edge Points to draw the triangle. 

        :param buff: The buff to edit.
        :type buff: Buff.
        :param p1: Upper triangle vertex.
        :param p2: Left-Lower triangle vertex.
        :param p3: Right-Lower triangle vertex.
        :type p1: Point.
        :type p2: Point.
        :type p3: Point.
        :param doSmooth: Color smooth filling control flag.
        :type doSmooth: bool.
        :param doAA: Anti-aliasing control flag.
        :type doAA: bool.
        :param doAAlevel: Anti-aliasing super sampling level.
        :type doAAlevel: int.
        :param doTexture: Draw triangle with texture control flag.
        :type doTexture: bool.
        :rtype: None.
    """

    def drawUpperTriangle(self, buff, p1, p2, p3, doSmooth=True, doAA=False, doAAlevel=4, doTexture=False):
        # Pre-Preparation
        upper_coords = p1.getCoords()
        lower_coords_1 = p2.getCoords()
        lower_coords_2 = p3.getCoords()

        # Initial Lines
        edge_one_points = self.lineMaker.drawLine(buff, p1, p2, doSmooth, doAA, doAAlevel)
        edge_two_points = self.lineMaker.drawLine(buff, p1, p3, doSmooth, doAA, doAAlevel)

        sorted_points_one = self.sortPoints(edge_one_points, p1, p2)
        sorted_points_two = self.sortPoints(edge_two_points, p1, p3)

        # Loop Variables
        point_one = p1
        point_two = p1

        for x in range(upper_coords[1] - 1, lower_coords_1[1], -1):
            point_one = sorted_points_one.get(x, Point((point_one.getCoords()[0], x), point_one.getColor()))
            point_two = sorted_points_two.get(x, Point((point_two.getCoords()[0], x), point_two.getColor()))
            if point_one.getCoords()[0] == point_two.getCoords()[0]:
                continue
            self.lineMaker.drawLine(buff, point_one, point_two, doSmooth, doAA, doAAlevel)

        self.lineMaker.drawLine(buff, p2, p3, doSmooth, doAA, doAAlevel)

        return

    """
        Draw the lower part of a compound Triangle. Calls the DrawLine function to do the heavy lifting, as well as 
        the SortPoints function to assist in getting the edge Points to draw the triangle. 

        :param buff: The buff to edit.
        :type buff: Buff.
        :param p1: Lower triangle vertex.
        :param p2: Left-Upper triangle vertex.
        :param p3: Right-Upper triangle vertex.
        :type p1: Point.
        :type p2: Point.
        :type p3: Point.
        :param doSmooth: Color smooth filling control flag.
        :type doSmooth: bool.
        :param doAA: Anti-aliasing control flag.
        :type doAA: bool.
        :param doAAlevel: Anti-aliasing super sampling level.
        :type doAAlevel: int.
        :param doTexture: Draw triangle with texture control flag.
        :type doTexture: bool.
        :rtype: None.
    """

    def drawLowerTriangle(self, buff, p1, p2, p3, doSmooth=True, doAA=False, doAAlevel=4, doTexture=False):

        # Pre-Preparation
        lower_coords = p1.getCoords()
        upper_coords_1 = p2.getCoords()
        upper_coords_2 = p3.getCoords()

        # Initial Lines
        edge_one_points = self.lineMaker.drawLine(buff, p1, p2, doSmooth, doAA, doAAlevel)
        edge_two_points = self.lineMaker.drawLine(buff, p1, p3, doSmooth, doAA, doAAlevel)

        sorted_points_one = self.sortPoints(edge_one_points, p1, p2)
        sorted_points_two = self.sortPoints(edge_two_points, p1, p3)

        # Loop Variables
        point_one = p1
        point_two = p1

        for x in range(lower_coords[1] + 2, upper_coords_1[1]):
            point_one = sorted_points_one.get(x, Point((point_one.getCoords()[0], x), point_one.getColor()))
            point_two = sorted_points_two.get(x, Point((point_two.getCoords()[0], x), point_two.getColor()))
            if point_one.getCoords()[0] == point_two.getCoords()[0]:
                continue

            self.lineMaker.drawLine(buff, point_one, point_two, doSmooth, doAA, doAAlevel)

        self.lineMaker.drawLine(buff, p2, p3, doSmooth, doAA, doAAlevel)

        return

    """
        Static Helper Function to sort the points of a line into a Dictionary, keyd by the Y value of the point, 
        that either holds the leftmost, or rightmost point in that Y value, depending on the direction of the line.

        :param linePointsToSort: List of Points in the Line that need sorting.
        :type linePointsToSort: List<Point>
        :param p1: First Point of the Line. Must be the unique Vertex of the Triangle.
        :param p2: Final Point of the Line. Must represent one of the points of the Horizontal Face of the Triangle.
        :type p1: Point.
        :type p2: Point.
        :rtype: Dict<int, Point>.
    """

    @staticmethod
    def sortPoints(linePointsToSort, p1, p2):
        # Pre-Preparation
        sorted_points = {}
        is_to_the_right = p2.getCoords()[0] - p1.getCoords()[0] > 0

        # Sorting Loop
        for point in linePointsToSort:
            coords = point.getCoords()
            cur_element = sorted_points.get(coords[1], point)

            if (is_to_the_right and cur_element.getCoords()[0] <= coords[0]) or ((not is_to_the_right) and
                                                                                 cur_element.getCoords()[0] >= coords[
                                                                                     0]):
                sorted_points[coords[1]] = point

        return sorted_points

    """
        Static Helper Function to sort the vertices of an object (triangle), either first by X and then by Y, 
        or vice versa. This double sorting allows for conflict resolution in the second sorting, since python will 
        tie-break by picking the object that showed up first in the original list.

        :param p1: First triangle vertex
        :param p2: Second triangle vertex
        :param p3: Third triangle vertex
        :type p1: Point
        :type p2: Point
        :type p3: Point
        :rtype: Point, Point, Point.
    """

    @staticmethod
    def sortVertices(vertices, sortType):
        # Sort using X as the VIP
        if sortType == "x":
            sorted_y = sorted(vertices, key=lambda point: point.getCoords()[1])
            sorted_x = sorted(sorted_y, key=lambda point: point.getCoords()[0])
            return sorted_x

        # Sort using Y as the VIP
        else:
            sorted_x = sorted(vertices, key=lambda point: point.getCoords()[0])
            sorted_y = sorted(sorted_x, key=lambda point: point.getCoords()[1])
            return sorted_y

    """
        Static Helper Function to confirm what shape the triple grouping of point is taking. This allows us to catch 
        corner cases (for example when all 3 points are actually the same point, or when they form a line), 
        as well as cases where we are already given a Lower or Upper triangle.

        :param p1: First triangle vertex
        :param p2: Second triangle vertex
        :param p3: Third triangle vertex
        :type p1: Point
        :type p2: Point
        :type p3: Point
        :rtype: String, Point, Point, Point.
    """

    @staticmethod
    def confirmTriangleShape(p1, p2, p3):
        # Pre-Preparation
        p1_coords = p1.getCoords()
        p2_coords = p2.getCoords()
        p3_coords = p3.getCoords()

        # X Line Case
        if (p1_coords[0] == p2_coords[0]) and (p2_coords[0] == p3_coords[0]):
            # Extraordinary Point Scenario
            if (p1_coords[1] == p2_coords[1]) and (p2_coords[1] == p3_coords[1]):
                return "point", p1, None, None

            # Actual X Line Case
            else:
                sorted_by_y = TriangleMaker.sortVertices([p1, p2, p3], "y")
                return "line", sorted_by_y[0], sorted_by_y[-1], None

        # Y Line Case
        elif (p1_coords[1] == p2_coords[1]) and (p2_coords[1] == p3_coords[1]):
            sorted_by_x = TriangleMaker.sortVertices([p1, p2, p3], "x")
            return "line", sorted_by_x[0], sorted_by_x[-1], None

        # Triangle with Horizontal P1 P2 base
        elif p1_coords[1] == p2_coords[1]:
            triangle_type = "lower_triangle" if p1_coords[1] > p3_coords[1] else "upper_triangle"
            sorted_by_x = TriangleMaker.sortVertices([p1, p2], "x")
            return triangle_type, p3, sorted_by_x[0], sorted_by_x[1]

        # Triangle with Horizontal P2 P3 base
        elif p2_coords[1] == p3_coords[1]:
            triangle_type = "lower_triangle" if p2_coords[1] > p1_coords[1] else "upper_triangle"
            sorted_by_x = TriangleMaker.sortVertices([p2, p3], "x")
            return triangle_type, p1, sorted_by_x[0], sorted_by_x[1]

        # Triangle with Horizontal P1 P3 Base
        elif p1_coords[1] == p3_coords[1]:
            triangle_type = "lower_triangle" if p1_coords[1] > p2_coords[1] else "upper_triangle"
            sorted_by_x = TriangleMaker.sortVertices([p1, p3], "x")
            return triangle_type, p2, sorted_by_x[0], sorted_by_x[1]

        # Complex Triangle
        else:
            sorted_by_y = TriangleMaker.sortVertices([p1, p2, p3], "y")
            return "complex_triangle", sorted_by_y[0], sorted_by_y[1], sorted_by_y[2]