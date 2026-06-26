//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   Game math utility library. Provides static methods for common vector operations including addition, subtraction,
//   scaling, normalization, dot product, and Hadamard product, as well as general-purpose numeric utilities such as
//   clamping, linear interpolation, and random number generation within a range.
//
//   All vector operations use double arrays to represent n-dimensional vectors, allowing flexible support for 2D, 3D,
//   and higher-dimensional calculations.
//
// TODO:
//
//   1. None.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: GMath
//
// Description:
//
//   A utility class providing a suite of static game-related math functions. All vector operations use double arrays
//   to represent n-dimensional vectors, allowing flexible support for 2D, 3D, and higher-dimensional calculations.
//
//*********************************************************************************************************************

public class GMath
{
    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Constructor 1/1: GMath
    //
    // Description:
    //
    //   Default constructor.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public GMath ()
    {
    }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: setVector
    //
    // Description:
    //
    //   Initializes a vector by copying values from a variable argument list into the target array. Supports vectors
    //   of any dimension.
    //
    //   Example 1 (2D vector):
    //
    //     double[] v2D = new double[2];
    //     GMath.setVector( v2D, 0.0, 1.0 );
    //
    //   Example 2 (3D vector):
    //
    //     double[] v3D = new double[3];
    //     GMath.setVector( v3D, 0.0, 1.0, 2.0 );
    //
    // Arguments:
    //
    //   v (double[]):
    //     Target vector array to populate with the given element values.
    //
    //   vx (double...):
    //     Variable argument list of element values to assign to the vector.
    //
    // Returns:
    //
    //   The input vector v, populated with the values from vx.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double[] setVector ( double[] v, double... vx )
    {
        for ( int i=0; i < vx.length; i++ )
        {
            v[i] = vx[i];
        }

        return v;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: addVector
    //
    // Description:
    //
    //   Performs component-wise vector addition on two vectors represented as arrays.
    //
    //   Precondition: v1 must be of equal or lower dimension than v2. This accommodates operations between vectors
    //   of mismatched dimension by using the smaller vector's length.
    //
    // Arguments:
    //
    //   v1[] (double):
    //     First vector operand.
    //
    //   v2[] (double):
    //     Second vector operand. Must have dimension greater than or equal to v1.
    //
    // Returns:
    //
    //   A new vector containing the component-wise sum of v1 and v2, or null if v1 has more elements than v2.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double[] addVector ( double v1[], double v2[] )
    {
        if ( v1.length <= v2.length )
        {
            double[] vectorSum = new double [ v1.length ];

            for ( int i = 0; i < v1.length; i++ )
            {
                vectorSum[i] = v1[i] + v2[i];
            }

            return vectorSum;
        }
        else
        {
            return null;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subVector
    //
    // Description:
    //
    //   Performs component-wise vector subtraction on two vectors represented as arrays. Both vectors must have the
    //   same dimension.
    //
    // Arguments:
    //
    //   v1[] (double):
    //     First vector operand (minuend).
    //
    //   v2[] (double):
    //     Second vector operand (subtrahend). Must have the same dimension as v1.
    //
    // Returns:
    //
    //   A new vector containing the component-wise difference v1 - v2, or null if the vectors have different
    //   dimensions.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double[] subVector ( double v1[], double v2[] )
    {
        if ( v1.length == v2.length )
        {
            double[] vectorSum = new double [ v1.length ];

            for ( int i = 0; i < v1.length; i++ )
            {
                vectorSum[i] = v1[i] - v2[i];
            }

            return vectorSum;
        }
        else
        {
            return null;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scaleVector
    //
    // Description:
    //
    //   Multiplies each element of a vector by a scalar factor.
    //
    //     v' = s · v
    //
    // Arguments:
    //
    //   v[] (double):
    //     The vector to scale.
    //
    //   s (double):
    //     Scalar multiplication factor.
    //
    // Returns:
    //
    //   A new vector with each element of v multiplied by s.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double[] scaleVector ( double v[], double s )
    {
        double[] scaledVector = new double [ v.length ];

        for ( int i = 0; i < v.length; i++ )
        {
            scaledVector[i] = v[i] * s;
        }

        return scaledVector;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: hadamardProduct
    //
    // Description:
    //
    //   Computes the Hadamard product (element-wise product) of two vectors. Each element of the result is the
    //   product of the corresponding elements of the input vectors.
    //
    //     result[i] = v1[i] · v2[i]
    //
    // Arguments:
    //
    //   v1[] (double):
    //     First vector operand.
    //
    //   v2[] (double):
    //     Second vector operand. Must have the same dimension as v1.
    //
    // Returns:
    //
    //   A new vector containing the element-wise product of v1 and v2, or null if the vectors have different
    //   dimensions.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double[] hadamardProduct ( double v1[], double v2[] )
    {
        if ( v1.length == v2.length )
        {
            double[] scaledVector = new double [ v1.length ];

            for ( int i = 0; i < v1.length; i++ )
            {
                scaledVector[i] = v1[i] * v2[i];
            }

            return scaledVector;
        }
        else
        {
            return null;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude
    //
    // Description:
    //
    //   Computes the Euclidean magnitude (length) of a vector.
    //
    //        _________________
    //       ╱   2     2     2
    //     ╲╱  v₁  + v₂  + v₃
    //
    // Arguments:
    //
    //   v[] (double):
    //     The vector whose magnitude is to be computed.
    //
    // Returns:
    //
    //   The Euclidean magnitude of vector v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double magnitude ( double v[] )
    {
        double s = 0;

        for ( int i = 0; i < v.length; i++ )
        {
            s += v[i] * v[i];
        }

        return Math.sqrt ( s );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: normalizeVector
    //
    // Description:
    //
    //   Returns the unit (normalized) vector in the direction of the input vector. The normalized vector is computed
    //   by dividing v by its magnitude:
    //
    //              v
    //     ────────────────────
    //        _________________
    //       ╱   2     2     2
    //     ╲╱  v₁  + v₂  + v₃
    //
    //   If the magnitude of v is zero, the vector cannot be normalized and null is returned.
    //
    // Arguments:
    //
    //   v[] (double):
    //     The vector to normalize.
    //
    // Returns:
    //
    //   The unit vector in the direction of v, or null if the magnitude of v is zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double[] normalizeVector ( double v[] )
    {
        double m = magnitude ( v );

        if ( m != 0)
        {
            return scaleVector ( v, 1/m );
        }
        else
        {
            return null;
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct
    //
    // Description:
    //
    //   Computes the scalar (dot) product of two vectors. The dot product is the sum of the products of
    //   corresponding elements:
    //
    //     v1 · v2 = v1[1]·v2[1] + v1[2]·v2[2] + ... + v1[n]·v2[n]
    //
    // Arguments:
    //
    //   v1[] (double):
    //     First vector operand.
    //
    //   v2[] (double):
    //     Second vector operand.
    //
    // Returns:
    //
    //   The scalar dot product of v1 and v2.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double dotProduct ( double v1[], double v2[] )
    {
        double dotProduct = 0;

        for ( int i = 0; i < v1.length; i++ )
        {
            dotProduct += v1[i] * v2[i];
        }

        return dotProduct;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: clamp
    //
    // Description:
    //
    //   Clamps a value to lie within the closed interval [min, max]. If the value is below min, returns min. If the
    //   value is above max, returns max. Otherwise, returns the value unchanged.
    //
    // Arguments:
    //
    //   value (double):
    //     The value to clamp.
    //
    //   min (double):
    //     Lower bound of the range (inclusive).
    //
    //   max (double):
    //     Upper bound of the range (inclusive).
    //
    // Returns:
    //
    //   The clamped value, guaranteed to satisfy min <= result <= max.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double clamp ( double value, double min, double max )
    {
        if ( value < min ) return min;
        if ( value > max ) return max;
        return value;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: lerp
    //
    // Description:
    //
    //   Performs linear interpolation between two values a and b by the factor t.
    //
    //     result = a + (b - a) · t
    //
    //   - When t = 0.0, returns a.
    //
    //   - When t = 1.0, returns b.
    //
    //   - When t = 0.5, returns the midpoint of a and b.
    //
    // Arguments:
    //
    //   a (double):
    //     Start value (returned when t = 0).
    //
    //   b (double):
    //     End value (returned when t = 1).
    //
    //   t (double):
    //     Interpolation factor, typically in the range [0, 1].
    //
    // Returns:
    //
    //   The linearly interpolated value between a and b.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double lerp ( double a, double b, double t )
    {
        return a + ( b - a ) * t;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: randomInRange
    //
    // Description:
    //
    //   Returns a pseudorandom double uniformly distributed in the closed interval [min, max].
    //
    // Arguments:
    //
    //   min (double):
    //     Lower bound of the range (inclusive).
    //
    //   max (double):
    //     Upper bound of the range (inclusive).
    //
    // Returns:
    //
    //   A pseudorandom double value in the range [min, max].
    //
    //-----------------------------------------------------------------------------------------------------------------

    public static double randomInRange ( double min, double max )
    {
        return min + Math.random () * ( max - min );
    }
}
