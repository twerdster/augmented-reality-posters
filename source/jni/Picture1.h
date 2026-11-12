/*
 * PROJECT_TAG
 * PICTURE1.h
 *
 * @file 
 *     Picture1.h
 *
 * @brief
 *     Geometry for the PICTURE1.
 */

#ifndef _QCAR_PICTURE1_OBJECT_H_
#define _QCAR_PICTURE1_OBJECT_H_


#define NUM_PICTURE1_OBJECT_VERTEX 824
#define NUM_PICTURE1_OBJECT_INDEX 1024 * 3
static const float picture1Vertices[4*3] =
{
		-0.4, -5, 5,
		12.4, -5, 5,
		-0.4,  5, 5,
		12.4,  5, 5
};

static const float picture1TexCoords[8] =
{
		0, 0,
		1, 0,
		0, 1,
		1, 1
};

static const unsigned short picture1Indices[6] =
{
    0,1,2,
    1,3,2
};

#endif // _QCAR_PICTURE1_OBJECT_H_
