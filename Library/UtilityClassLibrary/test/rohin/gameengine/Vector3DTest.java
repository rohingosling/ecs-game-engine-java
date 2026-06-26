//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the Vector3D class, covering constructors, accessors, mutators, arithmetic operations, magnitude,
//   normalization, dot product, cross product, and Euclidean distance.
//
// TODO:
//
//   1. Add tests for cross product magnitude verification.
//   2. Add tests for arithmetic operations with NaN and infinity components.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: Vector3DTest
//
// Description:
//
//   JUnit 5 test suite for the Vector3D class. Tests are organized by operation, with each section validating
//   correctness, edge cases, and immutability guarantees for the corresponding Vector3D methods.
//
//*********************************************************************************************************************

public class Vector3DTest
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
    //   Verifies that the default constructor initializes all three components to zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void defaultConstructor_createsZeroVector ()
    {
        Vector3D v = new Vector3D ();
        assertEquals ( 0.0, v.getX (), EPSILON );
        assertEquals ( 0.0, v.getY (), EPSILON );
        assertEquals ( 0.0, v.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: parameterizedConstructor_setsXYZ
    //
    // Description:
    //
    //   Verifies that the parameterized constructor assigns the specified x, y, and z values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void parameterizedConstructor_setsXYZ ()
    {
        Vector3D v = new Vector3D ( 3.0, 4.0, 5.0 );
        assertEquals ( 3.0, v.getX (), EPSILON );
        assertEquals ( 4.0, v.getY (), EPSILON );
        assertEquals ( 5.0, v.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: arrayConstructor_setsFromArray
    //
    // Description:
    //
    //   Verifies that the array constructor initializes components from a three-element array.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void arrayConstructor_setsFromArray ()
    {
        double[] arr = { 5.0, 6.0, 7.0 };
        Vector3D v = new Vector3D ( arr );
        assertEquals ( 5.0, v.getX (), EPSILON );
        assertEquals ( 6.0, v.getY (), EPSILON );
        assertEquals ( 7.0, v.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: copyConstructor_copiesValues
    //
    // Description:
    //
    //   Verifies that the copy constructor duplicates all three component values of the source vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void copyConstructor_copiesValues ()
    {
        Vector3D original = new Vector3D ( 7.0, 8.0, 9.0 );
        Vector3D copy = new Vector3D ( original );
        assertEquals ( 7.0, copy.getX (), EPSILON );
        assertEquals ( 8.0, copy.getY (), EPSILON );
        assertEquals ( 9.0, copy.getZ (), EPSILON );
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
        Vector3D original = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D copy = new Vector3D ( original );
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
        Vector3D v = new Vector3D ();
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
        Vector3D v = new Vector3D ();
        v.setY ( 20.0 );
        assertEquals ( 20.0, v.getY (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setZ_updatesZ
    //
    // Description:
    //
    //   Verifies that setZ updates the z component to the specified value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setZ_updatesZ ()
    {
        Vector3D v = new Vector3D ();
        v.setZ ( 30.0 );
        assertEquals ( 30.0, v.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_fromDoubles
    //
    // Description:
    //
    //   Verifies that setVector assigns all three components from individual double arguments.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_fromDoubles ()
    {
        Vector3D v = new Vector3D ();
        v.setVector ( 3.0, 4.0, 5.0 );
        assertEquals ( 3.0, v.getX (), EPSILON );
        assertEquals ( 4.0, v.getY (), EPSILON );
        assertEquals ( 5.0, v.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_fromArray
    //
    // Description:
    //
    //   Verifies that setVector assigns all three components from a three-element array.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_fromArray ()
    {
        Vector3D v = new Vector3D ();
        v.setVector ( new double[] { 5.0, 6.0, 7.0 } );
        assertEquals ( 5.0, v.getX (), EPSILON );
        assertEquals ( 6.0, v.getY (), EPSILON );
        assertEquals ( 7.0, v.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_fromVector3D
    //
    // Description:
    //
    //   Verifies that setVector copies all three components from another Vector3D instance.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_fromVector3D ()
    {
        Vector3D v = new Vector3D ();
        v.setVector ( new Vector3D ( 7.0, 8.0, 9.0 ) );
        assertEquals ( 7.0, v.getX (), EPSILON );
        assertEquals ( 8.0, v.getY (), EPSILON );
        assertEquals ( 9.0, v.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: getVector_returnsInternalArray
    //
    // Description:
    //
    //   Verifies that getVector returns an array containing the current x, y, and z values.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void getVector_returnsInternalArray ()
    {
        Vector3D v = new Vector3D ( 1.0, 2.0, 3.0 );
        double[] arr = v.getVector ();
        assertEquals ( 1.0, arr[0], EPSILON );
        assertEquals ( 2.0, arr[1], EPSILON );
        assertEquals ( 3.0, arr[2], EPSILON );
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
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 4.0, 5.0, 6.0 );
        Vector3D result = a.add ( b );
        assertEquals ( 5.0, result.getX (), EPSILON );
        assertEquals ( 7.0, result.getY (), EPSILON );
        assertEquals ( 9.0, result.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 5.0, -3.0, 2.0 );
        Vector3D b = new Vector3D ( -2.0, 7.0, -1.0 );
        Vector3D result = a.add ( b );
        assertEquals ( 3.0, result.getX (), EPSILON );
        assertEquals ( 4.0, result.getY (), EPSILON );
        assertEquals ( 1.0, result.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 4.0, 5.0, 6.0 );
        a.add ( b );
        assertEquals ( 1.0, a.getX (), EPSILON );
        assertEquals ( 2.0, a.getY (), EPSILON );
        assertEquals ( 3.0, a.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 5.0, 7.0, 9.0 );
        Vector3D b = new Vector3D ( 2.0, 3.0, 4.0 );
        Vector3D result = a.subtract ( b );
        assertEquals ( 3.0, result.getX (), EPSILON );
        assertEquals ( 4.0, result.getY (), EPSILON );
        assertEquals ( 5.0, result.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 5.0, 7.0, 9.0 );
        Vector3D b = new Vector3D ( 2.0, 3.0, 4.0 );
        a.subtract ( b );
        assertEquals ( 5.0, a.getX (), EPSILON );
        assertEquals ( 7.0, a.getY (), EPSILON );
        assertEquals ( 9.0, a.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 2.0, 3.0, 4.0 );
        Vector3D b = new Vector3D ( 5.0, 6.0, 7.0 );
        Vector3D result = a.multiply ( b );
        assertEquals ( 10.0, result.getX (), EPSILON );
        assertEquals ( 18.0, result.getY (), EPSILON );
        assertEquals ( 28.0, result.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 2.0, 3.0, 4.0 );
        Vector3D b = new Vector3D ( 5.0, 6.0, 7.0 );
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
        Vector3D a = new Vector3D ( 10.0, 20.0, 30.0 );
        Vector3D b = new Vector3D ( 2.0, 5.0, 6.0 );
        Vector3D result = a.divide ( b );
        assertEquals ( 5.0, result.getX (), EPSILON );
        assertEquals ( 4.0, result.getY (), EPSILON );
        assertEquals ( 5.0, result.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 1.0, 1.0, 1.0 );
        Vector3D b = new Vector3D ( 0.0, 0.0, 0.0 );
        Vector3D result = a.divide ( b );
        assertTrue ( Double.isInfinite ( result.getX () ) );
        assertTrue ( Double.isInfinite ( result.getY () ) );
        assertTrue ( Double.isInfinite ( result.getZ () ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale_multipliesAllComponents
    //
    // Description:
    //
    //   Verifies that scale multiplies all three components by the given scalar factor.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scale_multipliesAllComponents ()
    {
        Vector3D v = new Vector3D ( 3.0, 4.0, 5.0 );
        Vector3D result = v.scale ( 2.0 );
        assertEquals ( 6.0, result.getX (), EPSILON );
        assertEquals ( 8.0, result.getY (), EPSILON );
        assertEquals ( 10.0, result.getZ (), EPSILON );
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
        Vector3D v = new Vector3D ( 3.0, 4.0, 5.0 );
        Vector3D result = v.scale ( 0.0 );
        assertEquals ( 0.0, result.getX (), EPSILON );
        assertEquals ( 0.0, result.getY (), EPSILON );
        assertEquals ( 0.0, result.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale_byNegative_negatesAllComponents
    //
    // Description:
    //
    //   Verifies that scaling by -1 negates all three components.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scale_byNegative_negatesAllComponents ()
    {
        Vector3D v = new Vector3D ( 3.0, 4.0, 5.0 );
        Vector3D result = v.scale ( -1.0 );
        assertEquals ( -3.0, result.getX (), EPSILON );
        assertEquals ( -4.0, result.getY (), EPSILON );
        assertEquals ( -5.0, result.getZ (), EPSILON );
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
        Vector3D v = new Vector3D ( 3.0, 4.0, 5.0 );
        v.scale ( 10.0 );
        assertEquals ( 3.0, v.getX (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude_knownValue_122
    //
    // Description:
    //
    //   Verifies that magnitude returns 3.0 for the vector (1, 2, 2), since the Euclidean norm
    //   sqrt(1 + 4 + 4) = 3.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void magnitude_knownValue_122 ()
    {
        Vector3D v = new Vector3D ( 1.0, 2.0, 2.0 );
        assertEquals ( 3.0, v.magnitude (), EPSILON );
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
        Vector3D v = new Vector3D ( 0.0, 0.0, 0.0 );
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
        Vector3D v = new Vector3D ( -1.0, -2.0, -2.0 );
        assertEquals ( 3.0, v.magnitude (), EPSILON );
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
        Vector3D v = new Vector3D ( 0.0, 0.0, 5.0 );
        Vector3D n = v.norm ();
        assertEquals ( 0.0, n.getX (), EPSILON );
        assertEquals ( 0.0, n.getY (), EPSILON );
        assertEquals ( 1.0, n.getZ (), EPSILON );
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
        Vector3D v = new Vector3D ( 7.0, 11.0, 3.0 );
        Vector3D n = v.norm ();
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
        Vector3D v = new Vector3D ( 0.0, 0.0, 0.0 );
        Vector3D n = v.norm ();
        assertEquals ( 0.0, n.getX (), EPSILON );
        assertEquals ( 0.0, n.getY (), EPSILON );
        assertEquals ( 0.0, n.getZ (), EPSILON );
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
        Vector3D v = new Vector3D ( 3.0, 4.0, 5.0 );
        v.norm ();
        assertEquals ( 3.0, v.getX (), EPSILON );
        assertEquals ( 4.0, v.getY (), EPSILON );
        assertEquals ( 5.0, v.getZ (), EPSILON );
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
        Vector3D a = new Vector3D ( 1.0, 0.0, 0.0 );
        Vector3D b = new Vector3D ( 0.0, 1.0, 0.0 );
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
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 2.0, 4.0, 6.0 );
        assertEquals ( 28.0, a.dotProduct ( b ), EPSILON );
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
        Vector3D a = new Vector3D ( 1.0, 0.0, 0.0 );
        Vector3D b = new Vector3D ( -1.0, 0.0, 0.0 );
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
        Vector3D a = new Vector3D ( 2.0, 5.0, 3.0 );
        Vector3D b = new Vector3D ( 3.0, 7.0, 1.0 );
        assertEquals ( a.dotProduct ( b ), b.dotProduct ( a ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: crossProduct_iCrossJ_equalsK
    //
    // Description:
    //
    //   Verifies that i x j = k, producing the unit vector along the z-axis.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void crossProduct_iCrossJ_equalsK ()
    {
        Vector3D i = new Vector3D ( 1.0, 0.0, 0.0 );
        Vector3D j = new Vector3D ( 0.0, 1.0, 0.0 );
        Vector3D result = i.crossProduct ( j );
        assertEquals ( 0.0, result.getX (), EPSILON );
        assertEquals ( 0.0, result.getY (), EPSILON );
        assertEquals ( 1.0, result.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: crossProduct_jCrossI_equalsNegativeK
    //
    // Description:
    //
    //   Verifies that j x i = -k, confirming the anti-commutative property.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void crossProduct_jCrossI_equalsNegativeK ()
    {
        Vector3D i = new Vector3D ( 1.0, 0.0, 0.0 );
        Vector3D j = new Vector3D ( 0.0, 1.0, 0.0 );
        Vector3D result = j.crossProduct ( i );
        assertEquals ( 0.0, result.getX (), EPSILON );
        assertEquals ( 0.0, result.getY (), EPSILON );
        assertEquals ( -1.0, result.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: crossProduct_parallelVectors_returnsZeroVector
    //
    // Description:
    //
    //   Verifies that the cross product of two parallel vectors is a zero vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void crossProduct_parallelVectors_returnsZeroVector ()
    {
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 2.0, 4.0, 6.0 );
        Vector3D result = a.crossProduct ( b );
        assertEquals ( 0.0, result.getX (), EPSILON );
        assertEquals ( 0.0, result.getY (), EPSILON );
        assertEquals ( 0.0, result.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: crossProduct_isAntiCommutative
    //
    // Description:
    //
    //   Verifies that a x b = -(b x a) for arbitrary vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void crossProduct_isAntiCommutative ()
    {
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 4.0, 5.0, 6.0 );
        Vector3D axb = a.crossProduct ( b );
        Vector3D bxa = b.crossProduct ( a );
        assertEquals ( -axb.getX (), bxa.getX (), EPSILON );
        assertEquals ( -axb.getY (), bxa.getY (), EPSILON );
        assertEquals ( -axb.getZ (), bxa.getZ (), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: crossProduct_perpendicularToBothInputs
    //
    // Description:
    //
    //   Verifies that the cross product is perpendicular to both input vectors via dot product check.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void crossProduct_perpendicularToBothInputs ()
    {
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 4.0, 5.0, 6.0 );
        Vector3D c = a.crossProduct ( b );
        assertEquals ( 0.0, c.dotProduct ( a ), EPSILON );
        assertEquals ( 0.0, c.dotProduct ( b ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: distance_knownDistance
    //
    // Description:
    //
    //   Verifies the Euclidean distance between two known 3D points.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void distance_knownDistance ()
    {
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 4.0, 6.0, 3.0 );
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
        Vector3D a = new Vector3D ( 3.0, 4.0, 5.0 );
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
        Vector3D a = new Vector3D ( 1.0, 2.0, 3.0 );
        Vector3D b = new Vector3D ( 4.0, 6.0, 8.0 );
        assertEquals ( a.distance ( b ), b.distance ( a ), EPSILON );
    }
}
