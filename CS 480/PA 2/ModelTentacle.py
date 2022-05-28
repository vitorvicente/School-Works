"""
Model for our entire Octopus Creature

:author: vitor@bu.edu
:version: 1
"""

from Component import Component
from DisplayableTentacle import DisplayableTentacle
import copy

try:
    import OpenGL

    try:
        import OpenGL.GL as gl
        import OpenGL.GLU as glu
        import OpenGL.GLUT as glut
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
        import OpenGL.GLUT as glut
except ImportError:
    raise ImportError("Required dependency PyOpenGL not present")

"""
    Define our overall Octopus Tentacle model.
    Extends the Component Class.
"""


class ModelTentacle(Component):
    components = None
    rotationValues = None
    overallRotation = None
    rotationDirection = 1
    contextParent = None
    baseRotation = None
    scale = None

    """
        Init method. Sets up the actual Octopus Object. Makes a call to a helper method, with default Positioning 
        Matrix.

        :param position: Initial Position.
        :type position: Point.
        :param parent: Parent Component.
        :type parent: Component.
        :param baseRotation: Base rotation of the limb (along the Body).
        :type baseRotation: Integer.
        :param scale: Overall Scale Matrix.
        :type scale: 1x3 Integer Matrix.
        :param rotationDirection: Base Direction of the left/right Rotations.
        :type rotationDirection: Integer [1 or -1].
        :return: None.
    """

    def __init__(self, position, parent, baseRotation, scale=None,
                 rotationDirection=1):
        super().__init__(position, None)
        if scale is None:
            scale = [0.5, 0.5, 0.5]
        self.rotationValues = [[0, 0, 0], [-20, 0, 0], [-20, 0, 0]]
        self.rotationDirection = rotationDirection
        self.contextParent = parent
        self.scale = scale
        self.baseRotation = baseRotation

        self.display_obj = DisplayableTentacle(self.contextParent, self.baseRotation, self.scale, self.rotationValues)

    """
        Overriding the default Rotation Method, to account for proper Joint Rotation.
        
        :param degree: Degree of the rotation.
        :type degree: Integer.
        :param axis: Axis to rotate around.
        :type axis: 3x1 Integer Matrix.
        :return: None.
    """

    def rotate(self, degree, axis):
        if axis == self.uAxis:
            # Up/Down Rotation
            self.rotateU(degree)
        elif axis == self.wAxis:
            # Circle Rotation
            self.rotateW(self.rotationDirection*degree)
        else:
            # Right/Left Rotation
            self.rotateV(self.rotationDirection*degree)

        self.display_obj = DisplayableTentacle(self.contextParent, self.baseRotation, self.scale, self.rotationValues)

    """
        Method exclusively for Rotating Around the uAxis.
        
        Seems a lot more complicated than it actually is, it just makes sure the joints move in a way that is 
        naturally, and that does not affect anything, it also handles the passing from one joint move to another, 
        as well as upper and lower limits.
        
        :param degree: Degree for the rotation.
        :type degree: Integer.
        :return: None.
    """
    def rotateU(self, degree):
        # Main Rotation Up/Down

        # Upper Bound for Main Rotation
        if self.rotationValues[0][0] < -70:
            # Secondary Rotation Upwards

            # Upper Bound for Secondary Rotation
            if self.rotationValues[1][0] < -50:
                # Final Rotation Upwards

                # Upper Bound for Final Rotation
                if self.rotationValues[2][0] + degree < -80:
                    pass

                # Lower Bound between Secondary and Final
                elif self.rotationValues[2][0] + degree > -20:
                    self.rotationValues[1][0] += degree

                # Default Case for the Final Rotation
                else:
                    self.rotationValues[2][0] += degree

            # Lower Bound between Main and Secondary
            elif self.rotationValues[1][0] + degree > -20:
                self.rotationValues[0][0] += degree

            # Default Case for the Secondary Rotation
            else:
                self.rotationValues[1][0] += degree

        # Lower Bound for Main Rotation
        elif self.rotationValues[0][0] > 10:
            # Secondary Rotation Downwards

            # Lower Bound for Secondary Rotation
            if self.rotationValues[1][0] > 25:
                # Final Rotation Downwards

                # Lower Bound for Final Rotation
                if self.rotationValues[2][0] + degree > 30:
                    pass

                # Upper Bound for the Final Rotation
                elif self.rotationValues[2][0] + degree < -20:
                    self.rotationValues[1][0] += degree

                # Default Case for the Final Rotation
                else:
                    self.rotationValues[2][0] += degree

            # Upper Bound between Main and Secondary
            elif self.rotationValues[1][0] + degree < -20:
                self.rotationValues[0][0] += degree

            # Default Case for the Secondary Rotation
            else:
                self.rotationValues[1][0] += degree

        # Default Case for the Main Rotation
        else:
            self.rotationValues[0][0] += degree

    """
        This rotation is far simpler, it sets a single broad limit, since the joints on an octopus don't actually 
        work that well in the wAxis.
    
        :param degree: Degree for the rotation.
        :type degree: Integer.
        :return: None.
    """

    def rotateW(self, degree):
        if -90 <= self.rotationValues[0][2] + degree <= 90:
            self.rotationValues[0][2] += degree

    """
        Method exclusively for Rotating Around the vAxis.

        Seems a lot more complicated than it actually is, it just makes sure the joints move in a way that is 
        naturally, and that does not affect anything, it also handles the passing from one joint move to another, 
        as well as upper and lower limits.

        :param degree: Degree for the rotation.
        :type degree: Integer.
        :return: None.
    """

    def rotateV(self, degree):
        # Main Rotation Left/Right

        # Left Bound for Main Rotation
        if self.rotationValues[0][1] < -10:
            # Secondary Rotation Left

            # Left Bound for Secondary Rotation
            if self.rotationValues[1][1] < -25:
                # Final Rotation Left

                # Left Bound for Final Rotation
                if self.rotationValues[2][1] + degree < -30:
                    pass

                # Right Bound for Final Rotation
                elif self.rotationValues[2][1] + degree > 0:
                    self.rotationValues[1][1] += degree

                # Default Case for Final Rotation
                else:
                    self.rotationValues[2][1] += degree

            # Right Bound for Secondary Rotation
            elif self.rotationValues[1][1] + degree > 0:
                self.rotationValues[0][1] += degree

            # Default Case for Secondary Rotation
            else:
                self.rotationValues[1][1] += degree

        # Right Bound for Main Rotation
        elif self.rotationValues[0][1] > 10:
            # Secondary Rotation Right

            # Right Bound for Secondary Rotation
            if self.rotationValues[1][1] > 25:
                # Final Rotation Right

                # Right Bound for Final Rotation
                if self.rotationValues[2][1] + degree > 30:
                    pass

                # Left Bound for Final Rotation
                elif self.rotationValues[2][1] + degree < 0:
                    self.rotationValues[1][1] += degree

                # Default Case for Final Rotation
                else:
                    self.rotationValues[2][1] += degree

            # Left Bound for Secondary Rotation
            elif self.rotationValues[1][1] + degree < 0:
                self.rotationValues[0][1] += degree

            # Default Case for Secondary Rotation
            else:
                self.rotationValues[1][1] += degree

        # Default Case for the Main Rotation
        else:
            self.rotationValues[0][1] += degree

    """
        Reset to default settings. Overwritten from the original to deal with the complex angles
        Mode should be "color", "position", "angle", "scale", or "all".
        If mode is "all", then reset everything to default value.

        :param mode: The thing you want to reset.
        :type mode: String.
        :return: None.
    """

    def reset(self, mode="all"):
        if mode in ["angle", "all"]:
            self.rotationValues = [[0, 0, 0], [-20, 0, 0], [-20, 0, 0]]

            self.display_obj = DisplayableTentacle(self.contextParent, self.baseRotation, self.scale,
                                                   self.rotationValues)
        if mode in ["color", "all"]:
            self.current_color = copy.deepcopy(self.default_color)
        if mode in ["position", "all"]:
            self.current_position = copy.deepcopy(self.default_position)
        if mode in ["scale", "all"]:
            self.current_scale = copy.deepcopy(self.default_scale)
