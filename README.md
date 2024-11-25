## Air Quality Index (AQI) Search Engine

This project is a simplified AQI search engine that allows users to search for real-time air quality information by city name. It comprises a Java-based backend and a React-based frontend to ensure a fast and responsive experience with caching mechanisms to optimize performance.

# Key Features

# Backend

-> Fetch AQI Data
->Connects to the AQI API (https://aqicn.org/api/) to fetch air quality details for a specified city.

->Caching Mechanism
->Implements in-memory caching to reduce redundant API calls for frequently searched cities.
->Cache expiry and maximum cache entries are configured for optimal performance.

->RESTful Endpoints
->GET /aqi?city={cityName}: Fetches AQI data for the specified city.

->Error Handling
->Handles invalid city names, API errors, and cache misses gracefully.

->Local Deployment
->Easily runnable as a standalone service.

# Frontend

->Search Functionality
->Provides a search bar for users to input city names.

->Data Display
->Displays AQI values, dominant pollutants, health advice, and other details in a visually appealing way.

->UI Design
->Built with React.js for a modern, responsive, and interactive interface.


# API Provider Details

->Provider: AQICN API
->Endpoints Used:
/feed/{city}/?token={API_TOKEN}: Fetch AQI data for the specified city.

->Rate Limit: Refer to the provider's documentation for limits.

->Key Parameters:
city: The name of the city to fetch AQI data for.
token: API token required for authentication (configured in application.properties).

# Setup and Run Instructions

->Prerequisites

Backend:
Java 17+
Maven

Frontend:
Node.js 16+
npm or yarn

# Steps to Run

1. Clone the Repository

git clone https://github.com/iamsunnyyz/aqi-search-engine  
cd aqi-search-engine  

2. Backend Setup
Navigate to the backend directory:

cd AQI  

->Configure the API details in application.properties:

aqi.api.url=https://api.waqi.info/feed/{city}/?token=  
aqi.api.token=your_api_token  

->Build and run the backend:

mvn clean install  
mvn spring-boot:run  

The backend service will be available at http://localhost:8080.

3. Frontend Setup
Navigate to the frontend directory:


cd ../aqi-frontend  

Install dependencies:

npm install  

->Start the React development server:

npm start  

The frontend will be available at http://localhost:3000.

# Caching Logic

->Implementation Details

In-Memory Cache:
A simple in-app caching mechanism using Spring's @Cacheable annotation.
Reduces redundant API calls for frequently searched cities.
Cache Expiry and Limit:

Cache entries expire after 10 hour.
Maximum of 100 entries stored at any time.

# Benefits
Speeds up data retrieval for repeated queries.
Reduces load on the AQI API, saving API call quota.