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

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management


## Download Pretrained model and Vocabulary
If you do not want to train the model from scratch, you can use a pretrained model. You can download the pretrained model [here](https://www.dropbox.com/s/ne0ixz5d58ccbbz/pretrained_model.zip?dl=0) and the vocabulary file [here](https://www.dropbox.com/s/26adb7y9m98uisa/vocap.zip?dl=0). You should extract pretrained_model.zip to `./image_caption/models/` and vocab.pkl to `./image_caption/data/` using `unzip` command.



# How to run an example?


First of all, ```git clone --recurse-submodules -j8``` our project and ```cd``` to the current project,

```sh
git clone --recurse-submodules -j8 https://github.com/yaodongyu/cs8524-pictureteller.git```
```

```sh
cd cs8524-pictureteller
```

### 0. Download Pretrained model and Vocabulary
Download the pretrained model [here](https://www.dropbox.com/s/ne0ixz5d58ccbbz/pretrained_model.zip?dl=0) and the vocabulary file [here](https://www.dropbox.com/s/26adb7y9m98uisa/vocap.zip?dl=0). 

Extract pretrained_model.zip to `./image_caption/models/` and vocab.pkl to `./image_caption/data/` using `unzip` command.



### 1. Package this project
```sh
mvn package
```
### 2. Move the ```.jar``` file under ```cs8524-pictureteller/```
```sh
mv target/pictureteller-0.0.1-SNAPSHOT.jar .
```

### 3. Deployment
```sh
java -jar pictureteller-0.0.1-SNAPSHOT.jar
```


### 4. Open a browser and have fun

1). To initialize a new user, type
```html
localhost:8080/user/new
```

2). To add an image to user #id, such as user 1, type
```html
localhost:8080/user/1/image
```

3). To show an image of user #id, such as user 1, type
```html
localhost:8080/user/1/show
```

### 5. How to use the h2 database?

1). Open h2 console
```html
localhost:8080/h2-console
```

2). Enter user name and related information, where
```html
spring.datasource.url=jdbc:h2:file:c:/temp/rcp_h2
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
```