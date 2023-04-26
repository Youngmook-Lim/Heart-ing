FROM node:18.13.0

# Set the working directory
WORKDIR /usr/src/app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install the project dependencies
RUN npm install

# Copy the rest of the application files to the working directory
COPY . .

# Expose the port that the app runs on
EXPOSE 8000

# Start the application
CMD ["node", "server.js"]
