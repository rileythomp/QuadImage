# QuadImage Documenation

#### Usage Steps

Can be run from IntelliJ or the command line:

1. The application takes one image effect as input, as a program letter.

| Program Letter | Image Effect          
| ---    | ---   
| q      | QuadImage
| b      | Box blur
| e      | Laplacian edge detection
| t      | Twist image

2. The program also takes an image as input, as a path to the image from the project root.

```$ ./run.sh {program_letter} {image_path}```

##### QuadImage

1. Choose if you'd like black grid lines around each quadrant

2. Choose how many iterations you would like to perform

3. Choose if you'd like to create images for a gif

It will create an image called ${image_name}quad${iterations}.jpg

NB: If you would like to create images for a gif, then you must first create the directory frames/${image_name}:

```$ mkdir frames/${image_name}```

Choosing to create images for a gif will create up to 300 images in the frames directory mentioned above.

To create a gif:

```$ ./gif.sh ${image_name}```

Note: It can take a few minutes to finish creating the gif

##### Blur

1. Select the blur level (an integer from 1 to 99, with 1 being unblurred and 99 being very blurry).

2. It will create an image called ${image_name}blurred${blur_level}.jpg

Note: It may take a few seconds to finish creating the blurred image.

##### Edge Detection

It will create an image called ${image_name}edges.jpg

##### Twist

1. Choose how many iterations you would like to perform.

2. It will create an image  called ${image_name}twist${iterations}.jpg




