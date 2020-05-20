# QuadImage
Image manipulation with quadtrees. The image is split into 4 quadrants, each of which is given an average colour based on the colours of the pixels in that quadrant. The quadrant with the largest average colour error (a metric similar to standard deviation), will then again be split into 4 quadrants. This process is repeated an arbitrary number of times, continuously increasing the clarity of the image.

![fieldgif](gifs/field.gif)

![chimpgif](gifs/chimp.gif) 

| Iterations | 20              | 200                 
| ---        | ---    | ---     
| Image      | ![chimp20](quadimages/20chimp.png) | ![chimp200](quadimages/200chimp.png) 



| 2000          | 20000         | Real Image
| ---        | ---    | ---     
| ![chimp2000](quadimages/2000chimp.png) | ![chimp20000](quadimages/20000chimp.png) | ![chimp](images/chimp.png)

Inspiration from https://www.michaelfogleman.com/projects/quads/
