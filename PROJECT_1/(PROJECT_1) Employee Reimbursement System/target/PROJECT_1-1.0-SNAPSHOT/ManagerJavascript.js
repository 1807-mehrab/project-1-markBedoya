




function viewTickets() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax viewTicket Worked!");
            var ticketlist = ``;
            var tickets = JSON.parse(xhr.responseText);
           
            for (var i=0; i<tickets.length; i++) {
                let buttonColor = "";
                if (tickets[i].ticketStatus == "Pending") {
                    buttonColor = "btn-warning";
                }
                if (tickets[i].ticketStatus == "Accepted") {
                    buttonColor = "btn-success";
                }
                if (tickets[i].ticketStatus == "Denied") {
                    buttonColor = "";
                }
                
                //FOR DEBUG
                //make this a modal image.
                //https://www.w3schools.com/howto/howto_css_modal_images.asp
                //<img height="42" width="42" class="img-responsive" src="`+tickets[i].image+`" />
                //<img height="42" width="42" class="img-responsive" onclick='modalImage(`+tickets[i].image+`)' src="`+tickets[i].image+`" />
                //<img height="42" width="42" onclick='modalImage(`+tickets[i].image+`)' src="`+tickets[i].image+`" data-toggle="modal" data-target="#myModal"  />
                //<button type="button" class="btn btn-danger" onclick='modalImage(`+tickets[i].id+`)'>Delete</button>
                //<img height="42" width="42" onclick='modalImage(`+tickets[i].id+`) class="img-responsive" src="`+tickets[i].image+`"  />
                
                
                
                ticketlist += `
                <tr>
                    <td>`+tickets[i].ticketType+`</td>
                    <td>`+tickets[i].ticketDescription+`</td>
                    <td><button type="button" class="btn `+buttonColor+` btn-block" >`+tickets[i].ticketStatus+`</button></td>
                    <td><img height="42" width="42"
                        onclick=modalImage("`+tickets[i].image+`")
                        src="`+tickets[i].image+`" /></td>
                    <td>$`+tickets[i].totalAmount+`</td>
                    <td><button type="button" class="btn btn-danger" onclick='deleteTicket(`+tickets[i].id+`)'>Delete</button></td>
                </tr>`;
                 //console.log(tickets[i]);
                 
            } 
            document.getElementById('main').innerHTML = 
            `<h2>Your Reimbursements Requests:</h2>
            <table class="table table-striped table-bordered">
                <thead>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th>Amount</th>
                    <th>Actions</th>
                </thead>
                <tbody>`
                +ticketlist+
                `</tbody>
            </table>
            <br>
            <button class="btn btn-primary" id="createTicketButton" onclick='createTicket()'>Create Ticket</button>
            <br><br>
            <div id="createTicket"></div>
            `; 
            
              
        } 
    }

    xhr.open('GET', 'http://localhost:8084/PROJECT_1/list-ticket');
    xhr.send();
}

var submitTicketCounter;
function createTicket() {
    submitTicketCounter = 0;
    console.log("createTicket function was called");    
    document.getElementById('createTicket').innerHTML = 
        `<form id="createTicketForm">
            <div class="form-group">
              <label>Select Category:</label>
              <select multiple class="form-control" id="category">
                <option>Business Expense</option>
                <option>Auto Mileage and Travel</option>
                <option>Medical Expense</option>
              </select>
            </div>
            <div class="form-group">
              <label>Description:</label>
              <textarea class="form-control" id="description" rows="3" placeholder="What was this purchase for?"></textarea>
            </div>
            <div class="form-group">
              <label>Amount:</label>
              <input type="text" class="form-control" id="amount" placeholder="$ 00.00">
            </div>
            <div class="form-group">
              <label>Upload Receipt Image:</label>
              <div id="ticketLoadedMessage"></div>
              <input type="file" onchange="loadImage()" class="form-control-file" id="image">
            </div>
            <button type="button" class="btn btn-success" id="addTicketSubmited" onclick='addTicket()'>Submit Ticket</button>
          </form> 
          <br>
          <br>
          <br>
          <br>
          <br>
          <br>
          `;
}

var base64value = "";
function loadImage() {
    console.log("Load Imaged Called");
    document.getElementById('ticketLoadedMessage').innerHTML = `<p style="color:red">Image Not Ready!</p>`;
    
    base64value = "";
    var fileSelected = document.getElementById('createTicketForm').elements.item(3).files;
        if (fileSelected.length > 0) {
            var fileToLoad = fileSelected[0];
            var fileReader = new FileReader();
            //this fucntion converts image to base64 string
            fileReader.onload = function(fileLoadedEvent) {
                base64value = "";
                base64value = fileLoadedEvent.target.result;
                console.log(base64value);
                console.log("what is the string?");
                document.getElementById('ticketLoadedMessage').innerHTML = `<p style="color:green">Image Ready!</p>`;
            };
            //put incoming file image into the function to convert to base64
            fileReader.readAsDataURL(fileToLoad);
        }
}

function addTicket() {
    console.log("add-ticket ajax function called!");
 
    // for debug
    //console.log(document.getElementById('createTicketForm').elements.length);
    //console.log(document.getElementById('createTicketForm').elements.item(0).value);
    //console.log(document.getElementById('createTicketForm').elements.item(1).value);
    //console.log(document.getElementById('createTicketForm').elements.item(2).value);
    let json = "";
    let category = "";
    let description = "";
    let amount = "";
    category = document.getElementById('createTicketForm').elements.item(0).value;
    description = document.getElementById('createTicketForm').elements.item(1).value;
    amount = document.getElementById('createTicketForm').elements.item(2).value;
    
    

    json = JSON.stringify({
        //id : 0,
        ticketType : category,
        ticketDescription : description,
        totalAmount : amount,
        image : base64value
    });
    base64value = ""; //reset base64value
    
    console.log(json);
 
    if (category != "" && description != "" && amount != "") {
 
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            console.log(this.readyState);

            if (this.readyState == 4 && this.status == 200) {
                viewTickets();
                console.log("new ticket was sent!");
                document.getElementById('createTicket').innerHTML =``;
            }
        }

        xhr.open('POST', 'http://localhost:8084/PROJECT_1/add-ticket');
        xhr.send(json);
        
    } else {
        if (submitTicketCounter < 1) {
            submitTicketCounter = 1;
            document.getElementById('createTicket').insertAdjacentHTML("afterbegin", `<p style="color:red">Form is not complete!</p>`);
            console.log("error: did not complete ticket form!");
        }   
    }
    
    
}

function deleteTicket(id) {
    console.log("delete ticket id: " + id);
 
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            viewTickets();
            console.log("ticket was deleted!");
        }
    }
    
    xhr.open('POST', 'http://localhost:8084/PROJECT_1/delete-ticket');
    xhr.send(id);
    
}



function homePage() {
            document.getElementById('main').innerHTML = 
            `
                <h1>Manager Home Page!</h1>
                
                <!-- Heading Row -->
                <div class="row">
                  <div class="col-sm-8">
                    <img class="img-responsive" src="http://placehold.it/900x400" alt="">
                  </div>
                  <!-- /.col-lg-8 -->
                  <div class="col-sm-4">
                    <h1>Business Tagline</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem magni quas ex numquam, maxime minus quam molestias corporis quod, ea minima accusamus.</p>
                    <a class="btn btn-primary btn-lg" href="#">Call to Action!</a>
                  </div>
                  <!-- /.col-md-4 -->
                </div>
                <!-- /.row -->

                <!-- Call to Action Well -->
                <div class="row">
                    <br>
                    <br>
                    <div class="col-sm-12 bg-info well-lg vertical-align">
                        <div class="col-sm-9">
                                  <h2>Its a Call To Action</h2>
                        </div>
                        <div class="col-sm-3" >
                          <a href="#" class="btn btn-lg btn-block btn-success" >Go for It!</a>
                        </div>
                    </div>
                    <br>
                    <br>
                </div>

                <!-- Content Row -->
                <div class="row">
                  <div class="col-sm-4">
                    <div class="card h-100">
                      <div class="card-body">
                        <h2 class="card-title">Card One</h2>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem magni quas ex numquam, maxime minus quam molestias corporis quod, ea minima accusamus.</p>
                      </div>
                      <div class="card-footer">
                        <a href="#" class="btn btn-primary">More Info</a>
                      </div>
                    </div>
                  </div>
                  <!-- /.col-md-4 -->
                  <div class="col-sm-4">
                    <div class="card h-100">
                      <div class="card-body">
                        <h2 class="card-title">Card Two</h2>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Quod tenetur ex natus at dolorem enim! Nesciunt pariatur voluptatem sunt quam eaque, vel, non in id dolore voluptates quos eligendi labore.</p>
                      </div>
                      <div class="card-footer">
                        <a href="#" class="btn btn-primary">More Info</a>
                      </div>
                    </div>
                  </div>
                  <!-- /.col-md-4 -->
                  <div class="col-sm-4">
                    <div class="card h-100">
                      <div class="card-body">
                        <h2 class="card-title">Card Three</h2>
                        <p class="card-text">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Rem magni quas ex numquam, maxime minus quam molestias corporis quod, ea minima accusamus.</p>
                      </div>
                      <div class="card-footer">
                        <a href="#" class="btn btn-primary">More Info</a>
                      </div>
                    </div>
                  </div>
                  <!-- /.col-md-4 -->

                </div>
                <br>
                <br>
                <br>
                <br>
                <!-- /.row -->
            `; 
 
}



function viewEmployeeInfo() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax viewEmployeeInfo Worked!");
            var employee = JSON.parse(xhr.responseText);
            console.log(employee);
            
            //check the values are not null. if they are don't show 'null'
            
            let firstName = "";
            let lastName = "";
            let username = "";
            let phone = "";
            let email = "";
            let address = "";
            let city = "";
            let state = "";
            let country = "";
            let postalCode = "";
            
            if (employee.firstName !== null) {
                firstName = employee.firstName;
            }
            if (employee.lastName !== null) {
                lastName = employee.lastName;
            }
            if (employee.username !== null) {
                username = employee.username;
            }
            if (employee.phone !== null) {
                phone = employee.phone;
            }
            if (employee.email !== null) {
                email = employee.email;
            }
            if (employee.address !== null) {
                address = employee.address;
            }
            if (employee.city !== null) {
                city = employee.city;
            }
            if (employee.state !== null) {
                state = employee.state;
            }
            if (employee.country !== null) {
                country = employee.country;
            }
            if (employee.postalCode !== null) {
                postalCode = employee.postalCode;
            }
            
             document.getElementById('main').innerHTML = 
            `<h2>Your Profile Information:</h2>
            <div class="row">
                <div class="col-sm-4">
                    <table class="table" >
                        <thead>
                          <tr>
                            <th>Profile:</th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr>
                            <td>First Name:</td>
                            <td>`+firstName+`</td>
                          </tr>
                          <tr>
                            <td>Last Name:</td>
                            <td>`+lastName+`</td>
                          </tr>
                          <tr>
                            <td>Username:</td>
                            <td>`+username+`</td>
                          </tr>
                          <tr>
                            <td>Phone:</td>
                            <td>`+phone+`</td>
                          </tr>
                          <tr>
                            <td>Email:</td>
                            <td>`+email+`</td>
                          </tr>
                          <tr>
                            <td>Address:</td>
                            <td>`+address+`</td>
                          </tr>
                          <tr>
                            <td>City:</td>
                            <td>`+city+`</td>
                          </tr>
                          <tr>
                            <td>State:</td>
                            <td>`+state+`</td>
                          </tr>
                          <tr>
                            <td>Country:</td>
                            <td>`+country+`</td>
                          </tr>
                          <tr>
                            <td>Postal Code:</td>
                            <td>`+postalCode+`</td>
                          </tr>
                          <tr>
                            <td></td>
                            <td></td>
                          </tr>
                        </tbody>
                    </table>
                </div><!-- div col-sm4 -->
                <div class="col-sm-8">
                    <button class="btn btn-primary" onclick='updateInfo()'>Update Info</button>
                    <br><br>
                    <div id="updateInfo"></div>
                </div>
            </div><!-- row -->
            <br>
            `; 
           
            
              
        } 
    }

    xhr.open('GET', 'http://localhost:8084/PROJECT_1/profile');
    xhr.send();
}

function updateInfo() {
    console.log("UpdateInfo function was called");    
    document.getElementById('updateInfo').innerHTML = 
        `<form id="updateInfoForm">
            <div class="form-group">
              <label>Phone:</label>
              <input type="text" class="form-control" id="phone" placeholder="(123) 456-7890">
            </div>
            <div class="form-group">
              <label>Email:</label>
              <input type="text" class="form-control" id="email" placeholder="example@gmail.com">
            </div>
            <div class="form-group">
              <label>Address:</label>
              <input type="text" class="form-control" id="address" placeholder="1234 W. street">
            </div>
            <div class="form-group">
              <label>City:</label>
              <input type="text" class="form-control" id="city" placeholder="city name">
            </div>
            <div class="form-group">
              <label>State:</label>
              <input type="text" class="form-control" id="state" placeholder="TX">
            </div>
            <div class="form-group">
              <label>Country:</label>
              <input type="text" class="form-control" id="amount" placeholder="United States">
            </div>
            <div class="form-group">
              <label>Postal Code:</label>
              <input type="text" class="form-control" id="amount" placeholder="12345">
            </div>
            <button type="button" class="btn btn-success" onclick='changePersonalInfo()'>Submit Changes</button>
          </form> 
          <br>
          <br>
          <br>
          <br>
          <br>
          <br>
          `;
}

function changePersonalInfo() {
    console.log("change-Personal-Info ajax function called!");
 
    // for debug
    //console.log(document.getElementById('updateInfoForm').elements.length);
    //console.log(document.getElementById('updateInfoForm').elements.item(0).value);
    //console.log(document.getElementById('updateInfoForm').elements.item(1).value);
    //console.log(document.getElementById('updateInfoForm').elements.item(2).value);
    let json = "";
  
    let phone = "";
    let email = "";
    let address = "";
    let city = "";
    let state = "";
    let country = "";
    let postalCode = "";
    
//    if (document.getElementById('updateInfoForm').elements.item(0).value !== null) {
//        phone = document.getElementById('updateInfoForm').elements.item(0).value;
//    }
//    if (email = document.getElementById('updateInfoForm').elements.item(1).value !== null) {
//        email = email = document.getElementById('updateInfoForm').elements.item(1).value;
//    }
//    if (address = document.getElementById('updateInfoForm').elements.item(2).value !== null) {
//        address = address = document.getElementById('updateInfoForm').elements.item(2).value;
//    }
//    if (city = document.getElementById('updateInfoForm').elements.item(3).value !== null) {
//        city = city = document.getElementById('updateInfoForm').elements.item(3).value;
//    }
//    if (state = document.getElementById('updateInfoForm').elements.item(4).value !== null) {
//        state = state = document.getElementById('updateInfoForm').elements.item(4).value;
//    }
//    if (country = document.getElementById('updateInfoForm').elements.item(5).value !== null) {
//        country = country = document.getElementById('updateInfoForm').elements.item(5).value;
//    }
//    if (postalCode = document.getElementById('updateInfoForm').elements.item(6).value !== null) {
//        postalCode = postalCode = document.getElementById('updateInfoForm').elements.item(6).value;
//    }
            
    phone = document.getElementById('updateInfoForm').elements.item(0).value;
    email = document.getElementById('updateInfoForm').elements.item(1).value;
    address = document.getElementById('updateInfoForm').elements.item(2).value;
    city = document.getElementById('updateInfoForm').elements.item(3).value;
    state = document.getElementById('updateInfoForm').elements.item(4).value;
    country = document.getElementById('updateInfoForm').elements.item(5).value;
    postalCode = document.getElementById('updateInfoForm').elements.item(6).value;
    
    json = JSON.stringify({
        phone : phone,
        email : email,
        address : address,
        city : city,
        state : state,
        country : country,
        postalCode: postalCode
    });
    console.log(json);

        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            console.log(this.readyState);

            if (this.readyState == 4 && this.status == 200) {
                viewEmployeeInfo();
                console.log("Employee personal info has been changed!");
                document.getElementById('updateInfo').innerHTML =``;
            }
        }

        xhr.open('POST', 'http://localhost:8084/PROJECT_1/update-employee-profile');
        xhr.send(json);
        
    
}




function managerActions() {
    console.log("View Manager Actions!");

     document.getElementById('main').innerHTML = 
    `<h2>Manage Employee Reimbursement Request Tickets:</h2>
    <br>
    <br>
    <button class="btn btn-primary" onclick='viewAllPendingTickets()'>View All Pending Requests</button>
    &nbsp;&nbsp;<button class="btn btn-primary" onclick='viewAllResolvedTickets()'>View All Resolved Requests</button>
    &nbsp;&nbsp;<button class="btn btn-primary" onclick='viewAllEmployees()'>View All Employees</button>
    <br><br>
    <div id="ticketViews"></div>
    `; 

}

function viewAllPendingTickets() {
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax viewAllPendingTickets Worked!");
            var tickets = JSON.parse(xhr.responseText);
            getEmployee(tickets);
            //we have all pending tickets and now we need to all employee names to make the employee name matches.
            //literally viewing all tickets is in the getEmployee() right now
              
        } 
    }

    xhr.open('GET', 'http://localhost:8084/PROJECT_1/list-all-pending-tickets');
    xhr.send();
}

function getEmployee(tickets) {
    console.log("Called getEmployeeName() Ajax");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log("ready state from getEmployeeName:"+this.readyState);
        
        if (this.readyState == 4 && this.status == 200) {
            console.log("Get Employee from viewAllPendingTickets() sucsussful!");
            var employeeNames = "";
            employeeNames = JSON.parse(xhr.responseText);
            console.log("these are the employees: ");
            console.log(employeeNames);
            console.log("these are the tickets: ");
            console.log(tickets);
            
            //now match them and print them on the web page using dom
            var ticketlist = ``;
            for (var i=0; i<tickets.length; i++) {
                let buttonColor = "";
                if (tickets[i].ticketStatus == "Pending") {
                    buttonColor = "btn-warning";
                }
                if (tickets[i].ticketStatus == "Accepted") {
                    buttonColor = "btn-success";
                }
                if (tickets[i].ticketStatus == "Denied") {
                    buttonColor = "";
                }
                
                //get employee name from the ticket.employeeId field
                let ticketMakerName = "";
                for (var j=0; j<employeeNames.length; j++) { 
                    if (employeeNames[j].id == tickets[i].employeeId) {
                        ticketMakerName = employeeNames[j].firstName+ " " +employeeNames[j].lastName ;
                        console.log("name should be the same here: " + ticketMakerName);
                    }
                }
                
                var ticketString = "";
                ticketString = JSON.stringify({
                    id : tickets[i].id,
                    ticketStatus : tickets[i].ticketStatus,
                    managerId : tickets[i].managerId
                });
                
                ticketlist += `
                <tr>
                    <td>`+ticketMakerName+`</td> 
                    <td>`+tickets[i].ticketType+`</td>
                    <td>`+tickets[i].ticketDescription+`</td>
                    <td><button type="button" class="btn `+buttonColor+` btn-block" >`+tickets[i].ticketStatus+`</button></td>
                    <td><img height="42" width="42"
                        onclick=modalImage("`+tickets[i].image+`")
                        src="`+tickets[i].image+`" /></td>
                    <td>$`+tickets[i].totalAmount+`</td>
                    <td><button type="button" class="btn btn-success" onclick='acceptTicketFromPending(`+ticketString+`)'>Accept</button>&nbsp;&nbsp;
                        <button type="button" class="btn btn-danger" onclick='declineTicketFromPending(`+ticketString+`)'>Decline</button>
                    </td>
                </tr>`;
                 console.log(tickets[i]);
            } 
            document.getElementById('ticketViews').innerHTML = 
            `<h2>All Pending Requests:</h2>
            <table class="table table-striped table-bordered">
                <thead>
                    <th>Employee</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th>Amount</th>
                    <th>Actions</th>
                </thead>
                <tbody>`
                +ticketlist+
                `</tbody>
            </table>
            <br>
            <br>
            <br>
            <br>
            `; 
            
            
        }
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/get-all-employee-names');
    xhr.send();
}

function acceptTicketFromPending(ticket) {
    console.log("Sending this ticket to be accepted:");
    console.log(ticket);
    var ticketString = "";
    ticketString = JSON.stringify({
        id : ticket.id,
        ticketStatus : "Accept",
        managerId : ticket.managerId
    });
    console.log(ticketString);
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax acceptTicket Worked!");
            //reprint section
            viewAllPendingTickets();
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/update-ticket-status');
    xhr.send(ticketString);
}

function declineTicketFromPending(ticket) {
    console.log("Sending this ticket to be declined:");
    console.log(ticket);
    var ticketString = "";
    ticketString = JSON.stringify({
        id : ticket.id,
        ticketStatus : "Denied",
        managerId : ticket.managerId
    });
    console.log(ticketString);
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax declineTicket Worked!");
            //reprint section
            viewAllPendingTickets();
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/update-ticket-status');
    xhr.send(ticketString);
}






function viewAllResolvedTickets() {
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax viewAllResolvedTickets Worked!");
            var tickets = JSON.parse(xhr.responseText);
            getManagerAndEmployee(tickets);
            //we have all pending tickets and now we need to all employee names to make the employee name matches.
            //literally viewing all tickets is in the getEmployee() right now
              
        } 
    }

    xhr.open('GET', 'http://localhost:8084/PROJECT_1/list-all-resolved-tickets');
    xhr.send();
}

function getManagerAndEmployee(tickets) {
    console.log("Called getEmployeeName() Ajax");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log("ready state from getManagerAndEmployee:"+this.readyState);
        
        if (this.readyState == 4 && this.status == 200) {
            console.log("getManagerAndEmployee from viewAllResolvedTickets() sucsussful!");
            var employeeNames = "";
            employeeNames = JSON.parse(xhr.responseText);
            console.log("these are the employees: ");
            console.log(employeeNames);
            console.log("these are the tickets: ");
            console.log(tickets);
            
            //now match them and print them on the web page using dom
            var ticketlist = ``;
            for (var i=0; i<tickets.length; i++) {
                let buttonColor = "";
                if (tickets[i].ticketStatus == "Pending") {
                    buttonColor = "btn-warning";
                }
                if (tickets[i].ticketStatus == "Accepted") {
                    buttonColor = "btn-success";
                }
                if (tickets[i].ticketStatus == "Denied") {
                    buttonColor = "";
                }
                
                //get employee name from the ticket.employeeId field
                let ticketMakerName = "";
                for (var j=0; j<employeeNames.length; j++) { 
                    if (employeeNames[j].id == tickets[i].employeeId) {
                        ticketMakerName = employeeNames[j].firstName+ " " +employeeNames[j].lastName ;
                        //console.log("name should be the same here: " + ticketMakerName);
                    }
                }
                
                let ticketResolvedMangerName = "";
                for (var j=0; j<employeeNames.length; j++) { 
                    if (employeeNames[j].id == tickets[i].managerId) {
                        ticketResolvedMangerName = employeeNames[j].firstName+ " " +employeeNames[j].lastName ;
                        //console.log("name should be the same here: " + ticketMakerName);
                    }
                }
                
                
                var ticketString = "";
                ticketString = JSON.stringify({
                    id : tickets[i].id,
                    ticketStatus : tickets[i].ticketStatus,
                    managerId : tickets[i].managerId
                });
                

                ticketlist += `
                <tr>
                    <td>`+ticketMakerName+`</td> 
                    <td>`+tickets[i].ticketType+`</td>
                    <td>`+tickets[i].ticketDescription+`</td>
                    <td><button type="button" class="btn `+buttonColor+` btn-block" >`+tickets[i].ticketStatus+`</button></td>
                    <td><img height="42" width="42"
                        onclick=modalImage("`+tickets[i].image+`")
                        src="`+tickets[i].image+`" /></td>
                    <td>$`+tickets[i].totalAmount+`</td>
                    <td>`+ticketResolvedMangerName+`</td>
                    <td><button type="button" class="btn btn-warning" onclick='makeTicketPendingFromResolved(`+ticketString+`)'>Make Pending Again</button>  
                    </td>
                </tr>`;
                 console.log(tickets[i]);
            } 
            document.getElementById('ticketViews').innerHTML = 
            `<h2>All Resolved Requests:</h2>
            <table class="table table-striped table-bordered">
                <thead>
                    <th>Employee</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th>Amount</th>
                    <th>Resolved By Manager</th>
                    <th>Actions</th>
                </thead>
                <tbody>`
                +ticketlist+
                `</tbody>
            </table>
            <br>
            <br>
            <br>
            <br>
            `; 
            
            
        }
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/get-all-employee-names');
    xhr.send();
}

function makeTicketPendingFromResolved(ticket) {
    //delete manager id in ticket and set status back to pending
    console.log("Sending this ticket to be pending:");
    console.log(ticket);
    var ticketString = "";
    ticketString = JSON.stringify({
        id : ticket.id,
        ticketStatus : "Pending",
        managerId : ticket.managerId
    });
    console.log(ticketString);
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax makeTicketPendingFromResolved Worked!");
            //reprint section
            viewAllResolvedTickets();
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/update-ticket-status');
    xhr.send(ticketString);
}





function viewAllEmployees() {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax viewAllEmployees() Worked!");
            var employeelist = ``;
            var employees = JSON.parse(xhr.responseText);
           
            for (var i=0; i<employees.length; i++) {
                
                employeelist += `
                <tr>
                    <td>`+employees[i].firstName+`&nbsp;`+employees[i].lastName+`</td>
                    <td><button type="button" class="btn btn-primary" onclick='viewEmployeeTickets(`+employees[i].id+`)' >View Ticket Requests</button></td>
                </tr>`;
                 console.log(employees[i]);
            } 
            document.getElementById('ticketViews').innerHTML = 
            `<h2>All Employees:</h2>
            <table class="table table-striped table-bordered">
                <thead>
                    <th>Employee Name</th>
                    <th>Actions</th>
                </thead>
                <tbody>`
                +employeelist+
                `</tbody>
            </table>
            <div id="viewEmployeeTickets"></div>
            `;      
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/get-all-employee-names');
    xhr.send();
}

function viewEmployeeTickets(employeeId) {
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax viewEmployeeTickets Worked!");
            var tickets = JSON.parse(xhr.responseText);
            getManagerAndEmployeeForSingleEmployeeTickets(tickets, employeeId);
            //we have all pending tickets and now we need to all employee names to make the employee name matches.
            //literally viewing all tickets is in the getEmployee() right now
              
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/list-all-employee-tickets');
    xhr.send(employeeId);
}

function getManagerAndEmployeeForSingleEmployeeTickets(tickets, employeeId) {
    console.log("Called getManagerAndEmployeeForSingleEmployeeTickets() Ajax");
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log("ready state from getManagerAndEmployeeForSingleEmployeeTickets:"+this.readyState);
        
        if (this.readyState == 4 && this.status == 200) {
            console.log("getManagerAndEmployeeForSingleEmployeeTickets() sucsussful!");
            var employeeNames = "";
            employeeNames = JSON.parse(xhr.responseText);
            console.log("these are the employees: ");
            console.log(employeeNames);
            console.log("these are the tickets: ");
            console.log(tickets);
            
            //now match them and print them on the web page using dom
            var ticketlist = ``;
            for (var i=0; i<tickets.length; i++) {
                let buttonColor = "";
                if (tickets[i].ticketStatus == "Pending") {
                    buttonColor = "btn-warning";
                }
                if (tickets[i].ticketStatus == "Accepted") {
                    buttonColor = "btn-success";
                }
                if (tickets[i].ticketStatus == "Denied") {
                    buttonColor = "";
                }
                
                //get employee name from the ticket.employeeId field
                let ticketMakerName = "";
                for (var j=0; j<employeeNames.length; j++) { 
                    if (employeeNames[j].id == tickets[i].employeeId) {
                        ticketMakerName = employeeNames[j].firstName+ " " +employeeNames[j].lastName ;
                        //console.log("name should be the same here: " + ticketMakerName);
                    }
                }
                
                let ticketResolvedMangerName = "";
                for (var j=0; j<employeeNames.length; j++) { 
                    if (employeeNames[j].id == tickets[i].managerId) {
                        ticketResolvedMangerName = employeeNames[j].firstName+ " " +employeeNames[j].lastName ;
                        //console.log("name should be the same here: " + ticketMakerName);
                    }
                }
                
                
                var ticketString = "";
                ticketString = JSON.stringify({
                    id : tickets[i].id,
                    ticketStatus : tickets[i].ticketStatus,
                    managerId : tickets[i].managerId
                });

                ticketlist += `
                <tr>
                    <td>`+ticketMakerName+`</td> 
                    <td>`+tickets[i].ticketType+`</td>
                    <td>`+tickets[i].ticketDescription+`</td>
                    <td><button type="button" class="btn `+buttonColor+` btn-block" >`+tickets[i].ticketStatus+`</button></td>
                    <td><img height="42" width="42"
                            onclick=modalImage("`+tickets[i].image+`")
                            src="`+tickets[i].image+`" /></td>
                    <td>$`+tickets[i].totalAmount+`</td>
                    <td>`+ticketResolvedMangerName+`</td>
                    <td class="col-md-3" ><button type="button" class="btn btn-success" onclick='acceptTicketFromEmployee(`+ticketString+`, `+employeeId+`)'>Accept</button>
                        <button type="button" class="btn btn-warning" onclick='makeTicketPendingFromEmployee(`+ticketString+`, `+employeeId+`)'>Pending</button>
                        <button type="button" class="btn btn-danger" onclick='declineTicketFromEmployee(`+ticketString+`, `+employeeId+`)'>Decline</button>
                    </td>
                </tr>`;
                 console.log(tickets[i]);
            } 
            document.getElementById('viewEmployeeTickets').innerHTML = 
            `<h2>All Requests:</h2>
            <table class="table table-striped table-bordered">
                <thead>
                    <th>Employee</th>
                    <th>Category</th>
                    <th>Description</th>
                    <th>Status</th>
                    <th>Image</th>
                    <th>Amount</th>
                    <th>Resolved By Manager</th>
                    <th>Actions</th>
                </thead>
                <tbody>`
                +ticketlist+
                `</tbody>
            </table>
            <br>
            <br>
            <br>
            <br>
            `; 
            
            
        }
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/get-all-employee-names');
    xhr.send();
}

function acceptTicketFromEmployee(ticket, employeeId) {
    console.log("Sending this ticket to be accepted:");
    console.log(ticket);
    var ticketString = "";
    ticketString = JSON.stringify({
        id : ticket.id,
        ticketStatus : "Accept",
        managerId : ticket.managerId
    });
    console.log(ticketString);
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax acceptTicketFromEmployee Worked!");
            //reprint section
            viewEmployeeTickets(employeeId)
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/update-ticket-status');
    xhr.send(ticketString);
}

function declineTicketFromEmployee(ticket, employeeId) {
    console.log("Sending this ticket to be declined:");
    console.log(ticket);
    var ticketString = "";
    ticketString = JSON.stringify({
        id : ticket.id,
        ticketStatus : "Denied",
        managerId : ticket.managerId
    });
    console.log(ticketString);
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax declineTicketFromEmployee Worked!");
            //reprint section
            viewEmployeeTickets(employeeId)
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/update-ticket-status');
    xhr.send(ticketString);
}

function makeTicketPendingFromEmployee(ticket, employeeId) {
    //delete manager id in ticket and set status back to pending
    console.log("Sending this ticket to be pending:");
    console.log(ticket);
    var ticketString = "";
    ticketString = JSON.stringify({
        id : ticket.id,
        ticketStatus : "Pending",
        managerId : ticket.managerId
    });
    console.log(ticketString);
    
    let xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        console.log(this.readyState);

        if (this.readyState == 4 && this.status == 200) {
            console.log("Ajax makeTicketPendingFromEmployee Worked!");
            //reprint section
            viewEmployeeTickets(employeeId)
        } 
    }

    xhr.open('POST', 'http://localhost:8084/PROJECT_1/update-ticket-status');
    xhr.send(ticketString);
}







function modalImage(imageBase64) {
    document.getElementById('putModalImageHere').innerHTML = `<img class="img-responsive" src="`+imageBase64+`" />`;
    console.log("modal worked");
    //console.log(buffer1);
    $('#myModal').modal('show');  
}