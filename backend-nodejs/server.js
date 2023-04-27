const dotenv = require("dotenv");

dotenv.config({ path: "./config.env" });

const server = require("./app");

const PORT = process.env.PORT || 8000;

server.listen(PORT, () => {
  console.log(`Node.js server is running on port ${PORT}`);
});
