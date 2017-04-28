--luasocket 
local socket = require('socket')

--server ip address to connect
io.write("Choose server : ")
server = io.read()

--port no to use
io.write("Choose port : ")
port = io.read()

con = assert(socket.connect(server, port))

io.write("Successfully connected!\n")
	
io.write("Send a message : ")
	
msg = io.read()
	
while msg and msg ~= "" and not e do
	assert(con:send(msg .. "\n"))

	reply, err = con:receive()

	if not err then
		print("Reply from server: " .. reply)
	end

	io.write("Send a message : ")
	msg = io.read()
end

io.write("\n closed \n")