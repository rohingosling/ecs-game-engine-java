//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   2D vector class backed by a double array. Provides common vector operations including addition, subtraction,
//   Hadamard product, division, magnitude, normalization, scaling, dot product, distance, and angle calculation.
//
// TODO:
//
//   1. Add cross product support for 2D vectors (returns scalar z-component).
//   2. Add linear interpolation (lerp) method.
//   3. Add vector projection and rejection methods.
//   4. Add static factory methods for common vectors (zero, unit X, unit Y).
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: Vector2D
//
// Description:
//
//   Represents a 2D vector backed by an internal double array of length 2, where element indices are mapped to
//   constants X = 0 and Y = 1. Supports standard vector arithmetic, magnitude, normalization, dot product, Euclidean
//   distance, and angle calculation between vectors using the law of cosines.
//
//*********************************************************************************************************************

public class Vector2D
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    final static int X = 0;
    final static int Y = 1;

    private double[] v;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public double   getX      () { return this.v[X]; }
    public double   getY      () { return this.v[Y]; }
    public double[] getVector () { return this.v;    }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setX      ( double x )           { this.v[X] = x;                                }
    public void setY      ( double y )           { this.v[Y] = y;                                }
    public void setVector ( double x, double y ) { this.v[X] = x;         this.v[Y] = y;         }
    public void setVector ( double[] v )         { this.v[X] = v[X];      this.v[Y] = v[Y];      }
    public void setVector ( Vector2D v )         { this.v[X] = v.getX (); this.v[Y] = v.getY (); }


    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public Vector2D ()                     { initialize ( 0.0,       0.0       ); }
    public Vector2D ( double x, double y ) { initialize ( x,         y         ); }
    public Vector2D ( double[] v )         { initialize ( v[X],      v[Y]      ); }
    public Vector2D ( Vector2D v )         { initialize ( v.getX (), v.getY () ); }

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Allocates the internal double array and sets the X and Y components. Called by all constructors to centralize
    //   initialization logic.
    //
    // Arguments:
    //
    //   x (double):
    //     The initial value for the X component.
    //
    //   y (double):
    //     The initial value for the Y component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void initialize ( double x, double y)
    {
        v = new double[] { x, y };
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: add
    //
    // Description:
    //
    //   Computes the vector addition of this vector and the specified vector. Returns a new Vector2D containing the
    //   component-wise sum.
    //
    // Arguments:
    //
    //   v (Vector2D):
    //     The vector to add to this vector.
    //
    // Returns:
    //
    //   A new Vector2D containing the component-wise sum of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector2D add ( Vector2D v )
    {
        return new Vector2D ( this.v[X] + v.getX (), this.v[Y] + v.getY () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subtract
    //
    // Description:
    //
    //   Computes the vector subtraction of the specified vector from this vector. Returns a new Vector2D containing
    //   the component-wise difference.
    //
    // Arguments:
    //
    //   v (Vector2D):
    //     The vector to subtract from this vector.
    //
    // Returns:
    //
    //   A new Vector2D containing the component-wise difference of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector2D subtract ( Vector2D v )
    {
        return new Vector2D ( this.v[X] - v.getX (), this.v[Y] - v.getY () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: multiply
    //
    // Description:
    //
    //   Computes the Hadamard product (element-wise multiplication) of this vector and the specified vector. This is
    //   neither a dot product nor a cross product, but a straight element-by-element multiply.
    //
    // Arguments:
    //
    //   v (Vector2D):
    //     The vector to multiply with this vector, element by element.
    //
    // Returns:
    //
    //   A new Vector2D containing the element-wise product of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector2D multiply ( Vector2D v )
    {
        return new Vector2D ( this.v[X] * v.getX (), this.v[Y] * v.getY () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: divide
    //
    // Description:
    //
    //   Computes the element-wise division of this vector by the specified vector. This is a straight
    //   element-by-element division.
    //
    // Arguments:
    //
    //   v (Vector2D):
    //     The divisor vector. Each component of this vector is divided by the corresponding component of v.
    //
    // Returns:
    //
    //   A new Vector2D containing the element-wise quotient of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector2D divide ( Vector2D v )
    {
        return new Vector2D ( this.v[X] / v.getX (), this.v[Y] / v.getY () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude
    //
    // Description:
    //
    //   Computes the magnitude (Euclidean length) of this vector.
    //
    //        _________
    //       ╱  2    2
    //     ╲╱  x  + y
    //
    // Returns:
    //
    //   The magnitude of this vector as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double magnitude ()
    {
        // Retrieve vector.

        double x = this.v[X];
        double y = this.v[Y];

        // Calculate magnitude.

        return Math.sqrt ( x*x + y*y );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: norm
    //
    // Description:
    //
    //   Computes the normalized (unit) vector of this vector. The normalized vector has the same direction as the
    //   original but a magnitude of 1. If the magnitude of this vector is zero, the zero vector (0, 0) is returned.
    //
    //          v
    //     ────────────
    //        _________
    //       ╱  2    2
    //     ╲╱  x  + y
    //
    // Returns:
    //
    //   A new Vector2D representing the unit vector in the direction of this vector, or (0, 0) if the magnitude is
    //   zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector2D norm ()
    {
        // Retrieve vector.

        double x  = this.v[X];
        double y  = this.v[Y];

        // Calculate the magnitude of the vector.

        double m  = Math.sqrt ( x*x + y*y );

        // Initialize working variables.

        double Nx = 0;
        double Ny = 0;

        // Calculate the normalized vector.
        // If the magnitude of the vector is zero, then the normalized vector shall just remain as the zero vector (0,0).

        if ( m > 0 )
        {
            Nx = x / m;
            Ny = y / m;
        }

        // Return an instance of the normalized vector.

        return new Vector2D ( Nx, Ny );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale
    //
    // Description:
    //
    //   Scales this vector by a scalar factor. Returns a new Vector2D where each component is multiplied by the
    //   scalar value s.
    //
    // Arguments:
    //
    //   s (double):
    //     The scalar factor by which to scale this vector.
    //
    // Returns:
    //
    //   A new Vector2D with each component scaled by s.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector2D scale ( double s )
    {
        // Retrieve vector.

        double x  = this.v[X];
        double y  = this.v[Y];

        // Scale vector by s.

        double Sx = x * s;
        double Sy = y * s;

        // Return the scaled vector.

        return new Vector2D ( Sx, Sy );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct
    //
    // Description:
    //
    //   Computes the dot product of this vector and the specified vector.
    //
    //     A·B = Ax·Bx + Ay·By
    //
    // Arguments:
    //
    //   v (Vector2D):
    //     The second operand vector for the dot product calculation.
    //
    // Returns:
    //
    //   The scalar dot product of this vector and v as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double dotProduct ( Vector2D v )
    {
        // Retrieve vectors A and B.

        double Ax = this.v[X];
        double Ay = this.v[Y];
        double Bx = v.getX ();
        double By = v.getY ();

        // Calculate the dot product.

        return Ax*Bx + Ay*By;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: distance
    //
    // Description:
    //
    //   Computes the Euclidean distance between this vector and another vector, treating both as 2D points.
    //
    // Arguments:
    //
    //   other (Vector2D):
    //     The target point to measure distance to.
    //
    // Returns:
    //
    //   The Euclidean distance between the two points as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double distance ( Vector2D other )
    {
        double dx = other.getX () - this.v[X];
        double dy = other.getY () - this.v[Y];

        return Math.sqrt ( dx*dx + dy*dy );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: angleBetween
    //
    // Description:
    //
    //   Computes the angle between this vector and the specified vector using the law of cosines. If either vector
    //   has zero magnitude, the angle is returned as zero for robustness, even though this is not mathematically
    //   defined.
    //
    // Arguments:
    //
    //   v (Vector2D):
    //     The second vector used to calculate the angle relative to this vector.
    //
    // Returns:
    //
    //   The angle between the two vectors in radians as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double angleBetween ( Vector2D v )
    {
        // Retrieve vectors A and B.

        double Ax = this.v[X];
        double Ay = this.v[Y];
        double Bx = v.getX ();
        double By = v.getY ();

        // Calculate the difference between vectors A and B.

        double Cx = Bx - Ax;
        double Cy = By - Ay;

        // Calculate the magnitudes of vectors A, B and C.

        double Am = Math.sqrt ( Ax*Ax + Ay*Ay );
        double Bm = Math.sqrt ( Bx*Bx + By*By );
        double Cm = Math.sqrt ( Cx*Cx + Cy*Cy );

        // Initialize working variables.

        double angle = 0.0;
        double dx    = 0.0;
        double dy    = 1.0;

        // Calculate the angle between vectors A and B.
        //
        // Note:
        //
        // - We handle the zero conditions for vectors A and B, separately to the zero condition for vector C.
        //
        // - The reason we do this, is simply to high light the fact that within the context of correct linear algebra rules, the condition where either
        //   vector A or vector B is zero, is an error. Where as the condition where vector C is zero, simply results in an angle of zero.
        //
        // - In the conditional code complex below, we are going to allow the conditions of vectors A and B being zero, to fall through with an angle of zero.
        //
        // - This is not mathematically correct, but has negligible effect on the logic of programs who wish to use this method, and improves the robustness
        //   of the method.

        if ( ( Am > 0 ) && (Bm > 0 ) )
        {
            if ( Cm > 0 )
            {
                dx    = Am*Am + Bm*Bm - Cm*Cm;
                dy    = 2*Am*Bm;
                angle = Math.acos ( dx / dy );
            }
        }

        // Return the calculated angle.

        return angle;
    }
}
