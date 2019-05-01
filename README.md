# cs8524-pictureteller

This project lets user upload images and return a related sentence.


## Getting Started 

### Prerequisites

Java 8  
Maven  
Python 2.7  
PyTorch  
Numpy
pycocotools


### Usage example 
Change file path to your local path in /src/main/resources/resources.properties
```sh
# All user uploaded images stored in this path
imageService.file.path=NA
# Python path
imageService.python.path=NA
```

Package this project and ready to run.
```sh
mvn package
```

## Deployment 
Run application on a local computer.
```sh
java -jar some-jar.jar
```

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Download Pretrained model and Vocabulary
If you do not want to train the model from scratch, you can use a pretrained model. You can download the pretrained model [here](https://www.dropbox.com/s/ne0ixz5d58ccbbz/pretrained_model.zip?dl=0) and the vocabulary file [here](https://www.dropbox.com/s/26adb7y9m98uisa/vocap.zip?dl=0). You should extract pretrained_model.zip to `./image_caption/models/` and vocab.pkl to `./image_caption/data/` using `unzip` command.
