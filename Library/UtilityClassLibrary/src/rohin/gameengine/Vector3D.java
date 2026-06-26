//---------------------------------------------------------------------------------------------------------------------
// Project: ECS Game Engine
// Version: 2.0.0
// Date:    2014
// Author:  Rohin Gosling
//
// Description:
//
//   3D vector class backed by a double array of three elements. Provides standard vector operations including
//   addition, subtraction, Hadamard product, division, magnitude, normalization, scaling, dot product, cross product,
//   and Euclidean distance.
//
// TODO:
//
//   1. Add toString override for debug output.
//   2. Add equals and hashCode overrides.
//   3. Add static factory methods for common unit vectors.
//   4. Add angle-between method using dot product and magnitudes.
//   5. Add linear interpolation (lerp) method.
//
//---------------------------------------------------------------------------------------------------------------------

package rohin.gameengine;

//*********************************************************************************************************************
// Class: Vector3D
//
// Description:
//
//   Represents a three-dimensional vector with x, y, and z components stored in a double array. The class provides
//   constructors for initialization from scalar components, arrays, and other Vector3D instances, along with
//   accessors, mutators, and a full suite of vector arithmetic operations.
//
//*********************************************************************************************************************

public class Vector3D
{
    //=================================================================================================================
    // Fields
    //=================================================================================================================

    final static int X = 0;
    final static int Y = 1;
    final static int Z = 2;

    private double[] v;

    //=================================================================================================================
    // Accessors
    //=================================================================================================================

    public double getX () { return this.v[X]; }
    public double getY () { return this.v[Y]; }
    public double getZ  () { return this.v[Z]; }
    public double[] getVector () { return this.v;     }

    //=================================================================================================================
    // Mutators
    //=================================================================================================================

    public void setX      ( double x )                      { this.v[X] = x;                                                       }
    public void setY      ( double y )                      { this.v[Y] = y;                                                       }
    public void setZ      ( double z )                      { this.v[Z] = z;                                                       }
    public void setVector ( double x, double y, double z )  { this.v[X] = x;         this.v[Y] = y;         this.v[Z] = z;         }
    public void setVector ( double[] v )                    { this.v[X] = v[X];      this.v[Y] = v[Y];      this.v[Z] = v[Z];      }
    public void setVector ( Vector3D v )                    { this.v[X] = v.getX (); this.v[Y] = v.getY (); this.v[Z] = v.getZ (); }


    //=================================================================================================================
    // Constructors
    //=================================================================================================================

    public Vector3D ()                               { initialize ( 0.0, 0.0, 0.0 ); }
    public Vector3D ( double x, double y, double z ) { initialize ( x, y, z ); }
    public Vector3D ( double[] v )                   { initialize ( v[X], v[Y], v[Z] ); }
    public Vector3D ( Vector3D v )                   { initialize ( v.getX (), v.getY (), v.getZ () ); }

    // @formatter:on

    //=================================================================================================================
    // Methods
    //=================================================================================================================

    //-----------------------------------------------------------------------------------------------------------------
    // Method: initialize
    //
    // Description:
    //
    //   Common initialization method called by all constructors. Allocates the backing double array and assigns the
    //   x, y, and z component values.
    //
    // Arguments:
    //
    //   x (double):
    //     The initial value for the x component.
    //
    //   y (double):
    //     The initial value for the y component.
    //
    //   z (double):
    //     The initial value for the z component.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public void initialize ( double x, double y, double z )
    {
        v = new double[] { x, y, z };
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: add
    //
    // Description:
    //
    //   Computes the element-wise addition of this vector and the given vector, returning the result as a new
    //   Vector3D instance.
    //
    // Arguments:
    //
    //   v (Vector3D):
    //     The vector to add to this vector.
    //
    // Returns:
    //
    //   A new Vector3D containing the component-wise sum of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector3D add ( Vector3D v )
    {
        return new Vector3D ( this.v[X] + v.getX (), this.v[Y] + v.getY (), this.v[Z] + v.getZ () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: subtract
    //
    // Description:
    //
    //   Computes the element-wise subtraction of the given vector from this vector, returning the result as a new
    //   Vector3D instance.
    //
    // Arguments:
    //
    //   v (Vector3D):
    //     The vector to subtract from this vector.
    //
    // Returns:
    //
    //   A new Vector3D containing the component-wise difference of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector3D subtract ( Vector3D v )
    {
        return new Vector3D ( this.v[X] - v.getX (), this.v[Y] - v.getY (), this.v[Z] - v.getZ () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: multiply
    //
    // Description:
    //
    //   Computes the element-wise multiplication of this vector and the given vector, also known as the Hadamard
    //   product. Each component of the result is the product of the corresponding components of the two input
    //   vectors. Returns the result as a new Vector3D instance.
    //
    // Arguments:
    //
    //   v (Vector3D):
    //     The vector to multiply element-wise with this vector.
    //
    // Returns:
    //
    //   A new Vector3D containing the element-wise product of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector3D multiply ( Vector3D v )
    {
        return new Vector3D ( this.v[X] * v.getX (), this.v[Y] * v.getY (), this.v[Z] * v.getZ () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: divide
    //
    // Description:
    //
    //   Computes the element-wise division of this vector by the given vector. Each component of the result is the
    //   quotient of the corresponding components. Returns the result as a new Vector3D instance.
    //
    // Arguments:
    //
    //   v (Vector3D):
    //     The divisor vector whose components divide the corresponding components of this vector.
    //
    // Returns:
    //
    //   A new Vector3D containing the element-wise quotient of this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector3D divide ( Vector3D v )
    {
        return new Vector3D ( this.v[X] / v.getX (), this.v[Y] / v.getY (), this.v[Z] / v.getZ () );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: magnitude
    //
    // Description:
    //
    //   Computes the Euclidean magnitude (length) of this vector using the formula:
    //
    //        ______________
    //       ╱  2    2    2
    //     ╲╱  x  + y  + z
    //
    // Returns:
    //
    //   The magnitude of this vector as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double magnitude ()
    {
        double x = this.v[X];
        double y = this.v[Y];
        double z = this.v[Z];

        return Math.sqrt ( x*x + y*y + z*z );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: norm
    //
    // Description:
    //
    //   Computes the unit vector (normalized form) of this vector by dividing each component by the magnitude. If
    //   the magnitude is zero, returns the zero vector to avoid division by zero.
    //
    //             v
    //     ─────────────────
    //        ______________
    //       ╱  2    2    2
    //     ╲╱  x  + y  + z
    //
    // Returns:
    //
    //   A new Vector3D representing the unit vector in the direction of this vector, or (0, 0, 0) if the magnitude
    //   is zero.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector3D norm ()
    {
        double x = this.v[X];
        double y = this.v[Y];
        double z = this.v[Z];

        double m = Math.sqrt ( x*x + y*y + z*z );

        double Nx = 0;
        double Ny = 0;
        double Nz = 0;

        if ( m > 0 )
        {
            Nx = x / m;
            Ny = y / m;
            Nz = z / m;
        }

        return new Vector3D ( Nx, Ny, Nz );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: scale
    //
    // Description:
    //
    //   Scales this vector by a scalar multiplier, multiplying each component by the given value. Returns the result
    //   as a new Vector3D instance.
    //
    // Arguments:
    //
    //   s (double):
    //     The scalar multiplier applied to each component of this vector.
    //
    // Returns:
    //
    //   A new Vector3D with each component scaled by s.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector3D scale ( double s )
    {
        double x = this.v[X];
        double y = this.v[Y];
        double z = this.v[Z];

        double Sx = x * s;
        double Sy = y * s;
        double Sz = z * s;

        return new Vector3D ( Sx, Sy, Sz );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: dotProduct
    //
    // Description:
    //
    //   Computes the dot product (scalar product) of this vector and the given vector. The dot product is the sum
    //   of the products of corresponding components:
    //
    //     A · B = Ax·Bx + Ay·By + Az·Bz
    //
    // Arguments:
    //
    //   v (Vector3D):
    //     The vector to compute the dot product with.
    //
    // Returns:
    //
    //   The dot product as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double dotProduct ( Vector3D v )
    {
        double Ax = this.v[X];
        double Ay = this.v[Y];
        double Az = this.v[Z];
        double Bx = v.getX ();
        double By = v.getY ();
        double Bz = v.getZ ();

        return Ax*Bx + Ay*By + Az*Bz;
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: crossProduct
    //
    // Description:
    //
    //   Computes the cross product of this vector (A) and the given vector (B). The cross product yields a new
    //   vector perpendicular to both input vectors, computed using the determinant of a 3x3 matrix with unit vectors
    //   i, j, k in the first row, the components of A in the second row, and the components of B in the third row:
    //
    //     Cx = Ay·Bz - Az·By
    //
    //     Cy = Az·Bx - Ax·Bz
    //
    //     Cz = Ax·By - Ay·Bx
    //
    // Arguments:
    //
    //   v (Vector3D):
    //     The right-hand operand vector for the cross product.
    //
    // Returns:
    //
    //   A new Vector3D perpendicular to both this vector and v.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public Vector3D crossProduct ( Vector3D v )
    {
        double Ax = this.v[X];
        double Ay = this.v[Y];
        double Az = this.v[Z];
        double Bx = v.getX ();
        double By = v.getY ();
        double Bz = v.getZ ();

        double Cx = Ay*Bz - Az*By;
        double Cy = Az*Bx - Ax*Bz;
        double Cz = Ax*By - Ay*Bx;

        return new Vector3D ( Cx, Cy, Cz );
    }

    //-----------------------------------------------------------------------------------------------------------------
    // Method: distance
    //
    // Description:
    //
    //   Computes the Euclidean distance between this vector and another vector, treating both as points in 3D
    //   space. The distance is the magnitude of the difference vector.
    //
    // Arguments:
    //
    //   other (Vector3D):
    //     The target point to measure the distance to from this vector.
    //
    // Returns:
    //
    //   The Euclidean distance between the two points as a double.
    //
    //-----------------------------------------------------------------------------------------------------------------

    public double distance ( Vector3D other )
    {
        double dx = other.getX () - this.v[X];
        double dy = other.getY () - this.v[Y];
        double dz = other.getZ () - this.v[Z];

        return Math.sqrt ( dx*dx + dy*dy + dz*dz );
    }
}
