//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the Vector2D class, covering constructors, accessors, mutators, arithmetic operations, magnitude,
//   normalization, dot product, angle computation, and Euclidean distance.
//
// TODO:
//
//   1. Add tests for arithmetic operations with NaN and infinity components.
//   2. Add tests for the toString method output format.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: Vector2DTest
//
// Description:
//
//   JUnit 5 test suite for the Vector2D class. Tests are organized by operation, with each section validating
//   correctness, edge cases, and immutability guarantees for the corresponding Vector2D methods.
//
//*********************************************************************************************************************

public class Vector2DTest
{
    //=================================================================================================================
    // Constants
    //=================================================================================================================

    private static final double EPSILON = 1e-9;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: defaultConstructor_createsZeroVector
    //
    // Description:
    //
    //   Verifies that the default constructor initializes both components to zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultConstructor_createsZeroVector ()
    {
        Vector2D v = new Vector2D ();
        assertEquals ( 0.0, v.getX (), EPSILON );
        assertEquals ( 0.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: parameterizedConstructor_setsXY
    //
    // Description:
    //
    //   Verifies that the parameterized constructor assigns the specified x and y values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void parameterizedConstructor_setsXY ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        assertEquals ( 3.0, v.getX (), EPSILON );
        assertEquals ( 4.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: arrayConstructor_setsFromArray
    //
    // Description:
    //
    //   Verifies that the array constructor initializes components from a two-element array.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void arrayConstructor_setsFromArray ()
    {
        double[] arr = { 5.0, 6.0 };
        Vector2D v = new Vector2D ( arr );
        assertEquals ( 5.0, v.getX (), EPSILON );
        assertEquals ( 6.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: copyConstructor_copiesValues
    //
    // Description:
    //
    //   Verifies that the copy constructor duplicates the x and y values of the source vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void copyConstructor_copiesValues ()
    {
        Vector2D original = new Vector2D ( 7.0, 8.0 );
        Vector2D copy = new Vector2D ( original );
        assertEquals ( 7.0, copy.getX (), EPSILON );
        assertEquals ( 8.0, copy.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: copyConstructor_isIndependent
    //
    // Description:
    //
    //   Verifies that modifying the original vector does not affect the copy.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void copyConstructor_isIndependent ()
    {
        Vector2D original = new Vector2D ( 1.0, 2.0 );
        Vector2D copy = new Vector2D ( original );
        original.setX ( 99.0 );
        assertEquals ( 1.0, copy.getX (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setX_updatesX
    //
    // Description:
    //
    //   Verifies that setX updates the x component to the specified value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setX_updatesX ()
    {
        Vector2D v = new Vector2D ();
        v.setX ( 10.0 );
        assertEquals ( 10.0, v.getX (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setY_updatesY
    //
    // Description:
    //
    //   Verifies that setY updates the y component to the specified value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setY_updatesY ()
    {
        Vector2D v = new Vector2D ();
        v.setY ( 20.0 );
        assertEquals ( 20.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_fromDoubles
    //
    // Description:
    //
    //   Verifies that setVector assigns both components from individual double arguments.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_fromDoubles ()
    {
        Vector2D v = new Vector2D ();
        v.setVector ( 3.0, 4.0 );
        assertEquals ( 3.0, v.getX (), EPSILON );
        assertEquals ( 4.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_fromArray
    //
    // Description:
    //
    //   Verifies that setVector assigns both components from a two-element array.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_fromArray ()
    {
        Vector2D v = new Vector2D ();
        v.setVector ( new double[] { 5.0, 6.0 } );
        assertEquals ( 5.0, v.getX (), EPSILON );
        assertEquals ( 6.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_fromVector2D
    //
    // Description:
    //
    //   Verifies that setVector copies both components from another Vector2D instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_fromVector2D ()
    {
        Vector2D v = new Vector2D ();
        v.setVector ( new Vector2D ( 7.0, 8.0 ) );
        assertEquals ( 7.0, v.getX (), EPSILON );
        assertEquals ( 8.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getVector_returnsInternalArray
    //
    // Description:
    //
    //   Verifies that getVector returns an array containing the current x and y values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void getVector_returnsInternalArray ()
    {
        Vector2D v = new Vector2D ( 1.0, 2.0 );
        double[] arr = v.getVector ();
        assertEquals ( 1.0, arr[0], EPSILON );
        assertEquals ( 2.0, arr[1], EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: add_returnsCorrectSum
    //
    // Description:
    //
    //   Verifies that add returns a new vector with element-wise sums.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void add_returnsCorrectSum ()
    {
        Vector2D a = new Vector2D ( 1.0, 2.0 );
        Vector2D b = new Vector2D ( 3.0, 4.0 );
        Vector2D result = a.add ( b );
        assertEquals ( 4.0, result.getX (), EPSILON );
        assertEquals ( 6.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: add_withNegativeValues
    //
    // Description:
    //
    //   Verifies that add handles negative component values correctly.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void add_withNegativeValues ()
    {
        Vector2D a = new Vector2D ( 5.0, -3.0 );
        Vector2D b = new Vector2D ( -2.0, 7.0 );
        Vector2D result = a.add ( b );
        assertEquals ( 3.0, result.getX (), EPSILON );
        assertEquals ( 4.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: add_doesNotMutateOperands
    //
    // Description:
    //
    //   Verifies that add does not modify the original operand vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void add_doesNotMutateOperands ()
    {
        Vector2D a = new Vector2D ( 1.0, 2.0 );
        Vector2D b = new Vector2D ( 3.0, 4.0 );
        a.add ( b );
        assertEquals ( 1.0, a.getX (), EPSILON );
        assertEquals ( 2.0, a.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subtract_returnsCorrectDifference
    //
    // Description:
    //
    //   Verifies that subtract returns a new vector with element-wise differences.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void subtract_returnsCorrectDifference ()
    {
        Vector2D a = new Vector2D ( 5.0, 7.0 );
        Vector2D b = new Vector2D ( 2.0, 3.0 );
        Vector2D result = a.subtract ( b );
        assertEquals ( 3.0, result.getX (), EPSILON );
        assertEquals ( 4.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subtract_doesNotMutateOperands
    //
    // Description:
    //
    //   Verifies that subtract does not modify the original operand vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void subtract_doesNotMutateOperands ()
    {
        Vector2D a = new Vector2D ( 5.0, 7.0 );
        Vector2D b = new Vector2D ( 2.0, 3.0 );
        a.subtract ( b );
        assertEquals ( 5.0, a.getX (), EPSILON );
        assertEquals ( 7.0, a.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: multiply_returnsElementWiseProduct
    //
    // Description:
    //
    //   Verifies that multiply returns a new vector with element-wise products.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void multiply_returnsElementWiseProduct ()
    {
        Vector2D a = new Vector2D ( 2.0, 3.0 );
        Vector2D b = new Vector2D ( 4.0, 5.0 );
        Vector2D result = a.multiply ( b );
        assertEquals ( 8.0, result.getX (), EPSILON );
        assertEquals ( 15.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: multiply_doesNotMutateOperands
    //
    // Description:
    //
    //   Verifies that multiply does not modify the original operand vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void multiply_doesNotMutateOperands ()
    {
        Vector2D a = new Vector2D ( 2.0, 3.0 );
        Vector2D b = new Vector2D ( 4.0, 5.0 );
        a.multiply ( b );
        assertEquals ( 2.0, a.getX (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: divide_returnsElementWiseQuotient
    //
    // Description:
    //
    //   Verifies that divide returns a new vector with element-wise quotients.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void divide_returnsElementWiseQuotient ()
    {
        Vector2D a = new Vector2D ( 10.0, 20.0 );
        Vector2D b = new Vector2D ( 2.0, 5.0 );
        Vector2D result = a.divide ( b );
        assertEquals ( 5.0, result.getX (), EPSILON );
        assertEquals ( 4.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: divide_byZero_returnsInfinity
    //
    // Description:
    //
    //   Verifies that dividing by a zero vector produces infinite component values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void divide_byZero_returnsInfinity ()
    {
        Vector2D a = new Vector2D ( 1.0, 1.0 );
        Vector2D b = new Vector2D ( 0.0, 0.0 );
        Vector2D result = a.divide ( b );
        assertTrue ( Double.isInfinite ( result.getX () ) );
        assertTrue ( Double.isInfinite ( result.getY () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale_multipliesBothComponents
    //
    // Description:
    //
    //   Verifies that scale multiplies both components by the given scalar factor.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scale_multipliesBothComponents ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        Vector2D result = v.scale ( 2.0 );
        assertEquals ( 6.0, result.getX (), EPSILON );
        assertEquals ( 8.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale_byZero_returnsZeroVector
    //
    // Description:
    //
    //   Verifies that scaling by zero produces a zero vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scale_byZero_returnsZeroVector ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        Vector2D result = v.scale ( 0.0 );
        assertEquals ( 0.0, result.getX (), EPSILON );
        assertEquals ( 0.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale_byNegative_negatesBothComponents
    //
    // Description:
    //
    //   Verifies that scaling by -1 negates both components.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scale_byNegative_negatesBothComponents ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        Vector2D result = v.scale ( -1.0 );
        assertEquals ( -3.0, result.getX (), EPSILON );
        assertEquals ( -4.0, result.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale_doesNotMutateOriginal
    //
    // Description:
    //
    //   Verifies that scale does not modify the original vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scale_doesNotMutateOriginal ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        v.scale ( 10.0 );
        assertEquals ( 3.0, v.getX (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude_345Triangle
    //
    // Description:
    //
    //   Verifies that magnitude returns 5.0 for a (3, 4) vector, matching the 3-4-5 Pythagorean triple.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void magnitude_345Triangle ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        assertEquals ( 5.0, v.magnitude (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude_unitVectorX
    //
    // Description:
    //
    //   Verifies that magnitude returns 1.0 for a unit vector along the x-axis.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void magnitude_unitVectorX ()
    {
        Vector2D v = new Vector2D ( 1.0, 0.0 );
        assertEquals ( 1.0, v.magnitude (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude_zeroVector
    //
    // Description:
    //
    //   Verifies that magnitude returns zero for a zero vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void magnitude_zeroVector ()
    {
        Vector2D v = new Vector2D ( 0.0, 0.0 );
        assertEquals ( 0.0, v.magnitude (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude_negativeComponents
    //
    // Description:
    //
    //   Verifies that magnitude returns the correct positive length for a vector with negative components.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void magnitude_negativeComponents ()
    {
        Vector2D v = new Vector2D ( -3.0, -4.0 );
        assertEquals ( 5.0, v.magnitude (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: norm_returnsUnitVector
    //
    // Description:
    //
    //   Verifies that norm returns a unit vector with the correct component values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void norm_returnsUnitVector ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        Vector2D n = v.norm ();
        assertEquals ( 0.6, n.getX (), EPSILON );
        assertEquals ( 0.8, n.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: norm_magnitudeIsOne
    //
    // Description:
    //
    //   Verifies that the magnitude of the normalized result is exactly 1.0.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void norm_magnitudeIsOne ()
    {
        Vector2D v = new Vector2D ( 7.0, 11.0 );
        Vector2D n = v.norm ();
        assertEquals ( 1.0, n.magnitude (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: norm_zeroVector_returnsZeroVector
    //
    // Description:
    //
    //   Verifies that normalizing a zero vector returns a zero vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void norm_zeroVector_returnsZeroVector ()
    {
        Vector2D v = new Vector2D ( 0.0, 0.0 );
        Vector2D n = v.norm ();
        assertEquals ( 0.0, n.getX (), EPSILON );
        assertEquals ( 0.0, n.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: norm_doesNotMutateOriginal
    //
    // Description:
    //
    //   Verifies that norm does not modify the original vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void norm_doesNotMutateOriginal ()
    {
        Vector2D v = new Vector2D ( 3.0, 4.0 );
        v.norm ();
        assertEquals ( 3.0, v.getX (), EPSILON );
        assertEquals ( 4.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_perpendicularVectors_returnsZero
    //
    // Description:
    //
    //   Verifies that the dot product of two perpendicular unit vectors is zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_perpendicularVectors_returnsZero ()
    {
        Vector2D a = new Vector2D ( 1.0, 0.0 );
        Vector2D b = new Vector2D ( 0.0, 1.0 );
        assertEquals ( 0.0, a.dotProduct ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_parallelVectors
    //
    // Description:
    //
    //   Verifies the dot product of two parallel vectors against the expected scalar value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_parallelVectors ()
    {
        Vector2D a = new Vector2D ( 2.0, 3.0 );
        Vector2D b = new Vector2D ( 4.0, 6.0 );
        assertEquals ( 26.0, a.dotProduct ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_antiParallelVectors
    //
    // Description:
    //
    //   Verifies that the dot product of anti-parallel unit vectors is -1.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_antiParallelVectors ()
    {
        Vector2D a = new Vector2D ( 1.0, 0.0 );
        Vector2D b = new Vector2D ( -1.0, 0.0 );
        assertEquals ( -1.0, a.dotProduct ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_isCommutative
    //
    // Description:
    //
    //   Verifies that the dot product is commutative, i.e., a · b = b · a.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_isCommutative ()
    {
        Vector2D a = new Vector2D ( 2.0, 5.0 );
        Vector2D b = new Vector2D ( 3.0, 7.0 );
        assertEquals ( a.dotProduct ( b ), b.dotProduct ( a ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: angleBetween_perpendicularVectors
    //
    // Description:
    //
    //   Verifies that the angle between two perpendicular unit vectors is pi/2 radians.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void angleBetween_perpendicularVectors ()
    {
        Vector2D a = new Vector2D ( 1.0, 0.0 );
        Vector2D b = new Vector2D ( 0.0, 1.0 );
        assertEquals ( Math.PI / 2.0, a.angleBetween ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: angleBetween_sameDirection_returnsZero
    //
    // Description:
    //
    //   Verifies that the angle between two vectors pointing in the same direction is zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void angleBetween_sameDirection_returnsZero ()
    {
        Vector2D a = new Vector2D ( 3.0, 4.0 );
        Vector2D b = new Vector2D ( 6.0, 8.0 );
        assertEquals ( 0.0, a.angleBetween ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: angleBetween_zeroVectorA_returnsZero
    //
    // Description:
    //
    //   Verifies that the angle returns zero when the first vector is a zero vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void angleBetween_zeroVectorA_returnsZero ()
    {
        Vector2D a = new Vector2D ( 0.0, 0.0 );
        Vector2D b = new Vector2D ( 1.0, 0.0 );
        assertEquals ( 0.0, a.angleBetween ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: angleBetween_zeroVectorB_returnsZero
    //
    // Description:
    //
    //   Verifies that the angle returns zero when the second vector is a zero vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void angleBetween_zeroVectorB_returnsZero ()
    {
        Vector2D a = new Vector2D ( 1.0, 0.0 );
        Vector2D b = new Vector2D ( 0.0, 0.0 );
        assertEquals ( 0.0, a.angleBetween ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: distance_knownTriangle
    //
    // Description:
    //
    //   Verifies that the distance between (3, 0) and (0, 4) is 5.0, matching the 3-4-5 triangle.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void distance_knownTriangle ()
    {
        Vector2D a = new Vector2D ( 3.0, 0.0 );
        Vector2D b = new Vector2D ( 0.0, 4.0 );
        assertEquals ( 5.0, a.distance ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: distance_samePoint_returnsZero
    //
    // Description:
    //
    //   Verifies that the distance from a point to itself is zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void distance_samePoint_returnsZero ()
    {
        Vector2D a = new Vector2D ( 3.0, 4.0 );
        assertEquals ( 0.0, a.distance ( a ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: distance_isSymmetric
    //
    // Description:
    //
    //   Verifies that distance is symmetric, i.e., d(a, b) = d(b, a).
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void distance_isSymmetric ()
    {
        Vector2D a = new Vector2D ( 1.0, 2.0 );
        Vector2D b = new Vector2D ( 4.0, 6.0 );
        assertEquals ( a.distance ( b ), b.distance ( a ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: distance_negativeCoordinates
    //
    // Description:
    //
    //   Verifies correct distance computation with negative coordinates.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void distance_negativeCoordinates ()
    {
        Vector2D a = new Vector2D ( -3.0, -4.0 );
        Vector2D b = new Vector2D ( 0.0, 0.0 );
        assertEquals ( 5.0, a.distance ( b ), EPSILON );
    }
}
