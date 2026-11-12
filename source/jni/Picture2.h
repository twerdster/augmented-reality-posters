/*
 * PROJECT_TAG
 * PICTURE2.h
 *
 * @file 
 *     Picture2.h
 *
 * @brief
 *     Geometry for the PICTURE2.
 */

#ifndef _QCAR_PICTURE2_OBJECT_H_
#define _QCAR_PICTURE2_OBJECT_H_



static const float picture2Vertices[4*3] =
{
		-12.4, -5, 5,
		0.4,   -5, 5,
		-12.4,  5, 5,
		0.4,    5, 5
};

static const float picture2TexCoords[8] =
{
		0, 0,
		1, 0,
		0, 1,
		1, 1
};

static const unsigned short picture2Indices[6] =
{
    0,1,2,
    1,3,2
};

#endif // _QCAR_PICTURE2_OBJECT_H_

