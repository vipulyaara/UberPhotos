# Uber Photos
A small application that shows how to do networking and image caching in a scalable way without using any libraries

### Architecture
It is a very simplified version of MVVM. The fragment interacts with viewModel. I refrained from making the viewModel reactive as I thought the whole point of no-library-use was to to see how we can handle basics of OOPs.

### Networking
An instance of `UberNetworking` is responsible for executing network requests. Network requests are modelled through `Request` class. Response is returned in the form of `Success` or `ErrorResponse`.

### Dali
Dali is our image fetching and caching framework. It uses ThreadPoolExecutors to fetch images and then load them in the imageView. It has helpful extension functions on ImageView.

