# QuadImage Documenation

##### Usage Steps

1. The program takes a single image as input, as a path to the image from the project root.

2. Choose if you'd like black grid lines around each quadrant

3. Choose how many iterations you would like to perform

4. Choose if you'd like to create images for a gif

If you would like to create images for a gif, then you must first create the directory frames/${image_name}:

```$ mkdir frames/${image_name}```

Choosing to create images for a gif will create up to 300 images in the frames directory mentioned above.

Can be run from IntelliJ or the command line:

```$ ./run.sh```

To create a gif:

```$ ./gif.sh ${image_name}```

Note: It can take a few minutes to finish creating the gif





