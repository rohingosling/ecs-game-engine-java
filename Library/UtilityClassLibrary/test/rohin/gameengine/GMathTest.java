//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Unit tests for the GMath utility class, covering vector arithmetic, scalar operations, magnitude, normalization,
//   dot product, clamping, linear interpolation, and random number generation.
//
// TODO:
//
//   1. Add tests for edge cases with NaN and infinity inputs.
//   2. Add tests for negative scalar factors in scaleVector.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

//*********************************************************************************************************************
// Class: GMathTest
//
// Description:
//
//   JUnit 5 test suite for the GMath utility class. Each section targets a specific GMath method, validating
//   correctness across typical inputs, boundary conditions, and immutability guarantees.
//
//*********************************************************************************************************************

public class GMathTest
{
    //=================================================================================================================
    // Constants
    //=================================================================================================================

    private static final double EPSILON = 1e-9;

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_setsElementsInPlace
    //
    // Description:
    //
    //   Verifies that setVector assigns the specified values to the elements of the target array.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_setsElementsInPlace ()
    {
        double[] v = new double[3];
        GMath.setVector ( v, 1.0, 2.0, 3.0 );
        assertEquals ( 1.0, v[0], EPSILON );
        assertEquals ( 2.0, v[1], EPSILON );
        assertEquals ( 3.0, v[2], EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector_returnsTheSameArray
    //
    // Description:
    //
    //   Verifies that setVector returns a reference to the same array that was passed in.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void setVector_returnsTheSameArray ()
    {
        double[] v = new double[2];
        double[] result = GMath.setVector ( v, 5.0, 6.0 );
        assertSame ( v, result );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addVector_equalLength
    //
    // Description:
    //
    //   Verifies that addVector returns the element-wise sum of two equal-length vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addVector_equalLength ()
    {
        double[] v1 = { 1.0, 2.0, 3.0 };
        double[] v2 = { 4.0, 5.0, 6.0 };
        double[] result = GMath.addVector ( v1, v2 );
        assertArrayEquals ( new double[] { 5.0, 7.0, 9.0 }, result, EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addVector_v1ShorterThanV2
    //
    // Description:
    //
    //   Verifies that addVector returns a truncated sum when v1 is shorter than v2.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addVector_v1ShorterThanV2 ()
    {
        double[] v1 = { 1.0, 2.0 };
        double[] v2 = { 3.0, 4.0, 5.0 };
        double[] result = GMath.addVector ( v1, v2 );
        assertNotNull ( result );
        assertArrayEquals ( new double[] { 4.0, 6.0 }, result, EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addVector_v1LongerThanV2_returnsNull
    //
    // Description:
    //
    //   Verifies that addVector returns null when v1 is longer than v2.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addVector_v1LongerThanV2_returnsNull ()
    {
        double[] v1 = { 1.0, 2.0, 3.0 };
        double[] v2 = { 4.0, 5.0 };
        assertNull ( GMath.addVector ( v1, v2 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addVector_doesNotMutateInputs
    //
    // Description:
    //
    //   Verifies that addVector does not modify the input arrays.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void addVector_doesNotMutateInputs ()
    {
        double[] v1 = { 1.0, 2.0 };
        double[] v2 = { 3.0, 4.0 };
        GMath.addVector ( v1, v2 );
        assertEquals ( 1.0, v1[0], EPSILON );
        assertEquals ( 3.0, v2[0], EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subVector_equalLength
    //
    // Description:
    //
    //   Verifies that subVector returns the element-wise difference of two equal-length vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void subVector_equalLength ()
    {
        double[] v1 = { 5.0, 7.0, 9.0 };
        double[] v2 = { 1.0, 2.0, 3.0 };
        double[] result = GMath.subVector ( v1, v2 );
        assertArrayEquals ( new double[] { 4.0, 5.0, 6.0 }, result, EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subVector_mismatchedLength_returnsNull
    //
    // Description:
    //
    //   Verifies that subVector returns null when the input vectors have different lengths.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void subVector_mismatchedLength_returnsNull ()
    {
        double[] v1 = { 1.0, 2.0 };
        double[] v2 = { 3.0, 4.0, 5.0 };
        assertNull ( GMath.subVector ( v1, v2 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subVector_doesNotMutateInputs
    //
    // Description:
    //
    //   Verifies that subVector does not modify the input arrays.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void subVector_doesNotMutateInputs ()
    {
        double[] v1 = { 5.0, 7.0 };
        double[] v2 = { 1.0, 2.0 };
        GMath.subVector ( v1, v2 );
        assertEquals ( 5.0, v1[0], EPSILON );
        assertEquals ( 1.0, v2[0], EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scaleVector_scalesByFactor
    //
    // Description:
    //
    //   Verifies that scaleVector multiplies every element by the given scalar factor.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scaleVector_scalesByFactor ()
    {
        double[] v = { 2.0, 3.0, 4.0 };
        double[] result = GMath.scaleVector ( v, 3.0 );
        assertArrayEquals ( new double[] { 6.0, 9.0, 12.0 }, result, EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scaleVector_byZero_returnsZeroVector
    //
    // Description:
    //
    //   Verifies that scaleVector returns a zero vector when the scale factor is zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scaleVector_byZero_returnsZeroVector ()
    {
        double[] v = { 2.0, 3.0 };
        double[] result = GMath.scaleVector ( v, 0.0 );
        assertArrayEquals ( new double[] { 0.0, 0.0 }, result, EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scaleVector_doesNotMutateInput
    //
    // Description:
    //
    //   Verifies that scaleVector does not modify the input array.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void scaleVector_doesNotMutateInput ()
    {
        double[] v = { 2.0, 3.0 };
        GMath.scaleVector ( v, 5.0 );
        assertEquals ( 2.0, v[0], EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hadamardProduct_equalLength
    //
    // Description:
    //
    //   Verifies that hadamardProduct returns the element-wise product of two equal-length vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hadamardProduct_equalLength ()
    {
        double[] v1 = { 2.0, 3.0, 4.0 };
        double[] v2 = { 5.0, 6.0, 7.0 };
        double[] result = GMath.hadamardProduct ( v1, v2 );
        assertArrayEquals ( new double[] { 10.0, 18.0, 28.0 }, result, EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hadamardProduct_mismatchedLength_returnsNull
    //
    // Description:
    //
    //   Verifies that hadamardProduct returns null when the input vectors have different lengths.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hadamardProduct_mismatchedLength_returnsNull ()
    {
        double[] v1 = { 1.0, 2.0 };
        double[] v2 = { 3.0, 4.0, 5.0 };
        assertNull ( GMath.hadamardProduct ( v1, v2 ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hadamardProduct_doesNotMutateInputs
    //
    // Description:
    //
    //   Verifies that hadamardProduct does not modify the input arrays.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void hadamardProduct_doesNotMutateInputs ()
    {
        double[] v1 = { 2.0, 3.0 };
        double[] v2 = { 5.0, 6.0 };
        GMath.hadamardProduct ( v1, v2 );
        assertEquals ( 2.0, v1[0], EPSILON );
        assertEquals ( 5.0, v2[0], EPSILON );
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
        double[] v = { 3.0, 4.0 };
        assertEquals ( 5.0, GMath.magnitude ( v ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude_3dVector
    //
    // Description:
    //
    //   Verifies that magnitude returns 3.0 for the 3D vector (1, 2, 2), since the Euclidean norm
    //   sqrt(1 + 4 + 4) = 3.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void magnitude_3dVector ()
    {
        double[] v = { 1.0, 2.0, 2.0 };
        assertEquals ( 3.0, GMath.magnitude ( v ), EPSILON );
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
        double[] v = { 0.0, 0.0 };
        assertEquals ( 0.0, GMath.magnitude ( v ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: normalizeVector_returnsUnitVector
    //
    // Description:
    //
    //   Verifies that normalizeVector returns the correct unit vector components for a (3, 4) vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void normalizeVector_returnsUnitVector ()
    {
        double[] v = { 3.0, 4.0 };
        double[] result = GMath.normalizeVector ( v );
        assertNotNull ( result );
        assertEquals ( 0.6, result[0], EPSILON );
        assertEquals ( 0.8, result[1], EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: normalizeVector_magnitudeIsOne
    //
    // Description:
    //
    //   Verifies that the magnitude of the normalized result is exactly 1.0.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void normalizeVector_magnitudeIsOne ()
    {
        double[] v = { 7.0, 11.0 };
        double[] result = GMath.normalizeVector ( v );
        assertNotNull ( result );
        assertEquals ( 1.0, GMath.magnitude ( result ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: normalizeVector_zeroVector_returnsNull
    //
    // Description:
    //
    //   Verifies that normalizeVector returns null when given a zero vector.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void normalizeVector_zeroVector_returnsNull ()
    {
        double[] v = { 0.0, 0.0 };
        assertNull ( GMath.normalizeVector ( v ) );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: normalizeVector_doesNotMutateInput
    //
    // Description:
    //
    //   Verifies that normalizeVector does not modify the input array.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void normalizeVector_doesNotMutateInput ()
    {
        double[] v = { 3.0, 4.0 };
        GMath.normalizeVector ( v );
        assertEquals ( 3.0, v[0], EPSILON );
        assertEquals ( 4.0, v[1], EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_perpendicular_returnsZero
    //
    // Description:
    //
    //   Verifies that the dot product of two perpendicular unit vectors is zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_perpendicular_returnsZero ()
    {
        double[] v1 = { 1.0, 0.0 };
        double[] v2 = { 0.0, 1.0 };
        assertEquals ( 0.0, GMath.dotProduct ( v1, v2 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_parallel
    //
    // Description:
    //
    //   Verifies the dot product of two parallel 2D vectors against the expected scalar value.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_parallel ()
    {
        double[] v1 = { 2.0, 3.0 };
        double[] v2 = { 4.0, 6.0 };
        assertEquals ( 26.0, GMath.dotProduct ( v1, v2 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_3d
    //
    // Description:
    //
    //   Verifies the dot product computation for two 3D vectors.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_3d ()
    {
        double[] v1 = { 1.0, 2.0, 3.0 };
        double[] v2 = { 4.0, 5.0, 6.0 };
        assertEquals ( 32.0, GMath.dotProduct ( v1, v2 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct_isCommutative
    //
    // Description:
    //
    //   Verifies that the dot product is commutative, i.e., v1 · v2 = v2 · v1.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void dotProduct_isCommutative ()
    {
        double[] v1 = { 2.0, 5.0 };
        double[] v2 = { 3.0, 7.0 };
        assertEquals ( GMath.dotProduct ( v1, v2 ), GMath.dotProduct ( v2, v1 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clamp_valueInRange_returnsValue
    //
    // Description:
    //
    //   Verifies that clamp returns the value unchanged when it falls within the [min, max] range.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clamp_valueInRange_returnsValue ()
    {
        assertEquals ( 5.0, GMath.clamp ( 5.0, 0.0, 10.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clamp_valueBelowMin_returnsMin
    //
    // Description:
    //
    //   Verifies that clamp returns the minimum when the value is below the range.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clamp_valueBelowMin_returnsMin ()
    {
        assertEquals ( 0.0, GMath.clamp ( -3.0, 0.0, 10.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clamp_valueAboveMax_returnsMax
    //
    // Description:
    //
    //   Verifies that clamp returns the maximum when the value exceeds the range.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clamp_valueAboveMax_returnsMax ()
    {
        assertEquals ( 10.0, GMath.clamp ( 15.0, 0.0, 10.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clamp_valueEqualsMin_returnsMin
    //
    // Description:
    //
    //   Verifies that clamp returns the minimum when the value exactly equals the minimum.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clamp_valueEqualsMin_returnsMin ()
    {
        assertEquals ( 0.0, GMath.clamp ( 0.0, 0.0, 10.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clamp_valueEqualsMax_returnsMax
    //
    // Description:
    //
    //   Verifies that clamp returns the maximum when the value exactly equals the maximum.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void clamp_valueEqualsMax_returnsMax ()
    {
        assertEquals ( 10.0, GMath.clamp ( 10.0, 0.0, 10.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: lerp_atZero_returnsA
    //
    // Description:
    //
    //   Verifies that lerp returns the start value when t = 0.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void lerp_atZero_returnsA ()
    {
        assertEquals ( 2.0, GMath.lerp ( 2.0, 8.0, 0.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: lerp_atOne_returnsB
    //
    // Description:
    //
    //   Verifies that lerp returns the end value when t = 1.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void lerp_atOne_returnsB ()
    {
        assertEquals ( 8.0, GMath.lerp ( 2.0, 8.0, 1.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: lerp_atHalf_returnsMidpoint
    //
    // Description:
    //
    //   Verifies that lerp returns the midpoint when t = 0.5.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void lerp_atHalf_returnsMidpoint ()
    {
        assertEquals ( 5.0, GMath.lerp ( 2.0, 8.0, 0.5 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: lerp_negativeBeyondRange
    //
    // Description:
    //
    //   Verifies that lerp extrapolates below the start value when t < 0.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void lerp_negativeBeyondRange ()
    {
        assertEquals ( -4.0, GMath.lerp ( 2.0, 8.0, -1.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: lerp_beyondRange
    //
    // Description:
    //
    //   Verifies that lerp extrapolates beyond the end value when t > 1.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void lerp_beyondRange ()
    {
        assertEquals ( 14.0, GMath.lerp ( 2.0, 8.0, 2.0 ), EPSILON );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: randomInRange_withinBounds
    //
    // Description:
    //
    //   Verifies that randomInRange produces values within the specified [min, max] range over 100 iterations.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void randomInRange_withinBounds ()
    {
        double min = 5.0;
        double max = 10.0;
        for ( int i = 0; i < 100; i++ )
        {
            double result = GMath.randomInRange ( min, max );
            assertTrue ( result >= min, "Result " + result + " was below min " + min );
            assertTrue ( result <= max, "Result " + result + " was above max " + max );
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: randomInRange_sameMinMax_returnsThatValue
    //
    // Description:
    //
    //   Verifies that randomInRange returns the exact value when min equals max.
    //
    //-----------------------------------------------------------------------------------------------------------------

    @Test
    void randomInRange_sameMinMax_returnsThatValue ()
    {
        double value = 7.0;
        assertEquals ( value, GMath.randomInRange ( value, value ), EPSILON );
    }
}
