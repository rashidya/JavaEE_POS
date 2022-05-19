

var regExCustomerId = /^(C-)[0-9]{3}$/;
var regExPersonName = /^([A-z\s. ]{3,80})$/;
var regExAddress = /^([A-z0-9/,\s]{3,})$/;
var regExTel = /^([0][0-9]{9}|[0][0-9]{2}[-\s][0-9]{7})$/;


function validateCusId(e){
    if (regExCustomerId.test($("#cusId").val())) {
        $("#cusId").css('border-color', 'Green');
        $("#errorCustomerId").css('display', 'none');
        if (e == "Enter") {
            $("#name").focus();
        }

    } else {
        $("#cusId").css('border-color', 'Red');
        $("#errorCustomerId").css('display', 'block');

    }
}

function validateCusName(e){
    if (regExPersonName.test($("#name").val())) {
        $("#name").css('border-color', 'Green');
        $("#errorCustomerName").css('display', 'none');
        if (e == "Enter") {
            $("#address").focus();
        }

    } else {
        $("#name").css('border-color', 'Red');
        $("#errorCustomerName").css('display', 'block');

    }

}

function validateCusAddress(e){
    if (regExAddress.test($("#address").val())) {
        $("#address").css('border-color', 'Green');
        $("#errorCustomerAddress").css('display', 'none');
        if (e == "Enter") {
            $("#tel").focus();
        }

    } else {

        $("#address").css('border-color', 'Red');
        $("#errorCustomerAddress").css('display', 'block');
    }
}

function validateCusTel(e){
    if (regExTel.test($("#tel").val())) {
        $("#tel").css('border-color', 'Green');
        $("#errorCustomerTel").css('display', 'none');

        if ($('#AddCustomer').is(':enabled') && e == "Enter") {
            addCustomer();
            $("#cusId").focus();
        }

    } else {

        $("#tel").css('border-color', 'Red');
        $("#errorCustomerTel").css('display', 'block');
    }
}

/*
function enableAddCustomer() {
    if (customerNotExist() && regExCustomerId.test($("#cusId").val()) && regExPersonName.test($("#name").val()) && regExAddress.test($("#address").val()) && regExTel.test($("#tel").val())) {
        $("#AddCustomer").attr('disabled', false);
    } else {
        $("#AddCustomer").attr('disabled', true);
    }
}
*/


$("#cusId").keyup(function (e) {
    /*enableAddCustomer();*/
   validateCusId(e.key);
});

$("#name").keyup(function (e) {
   /* enableAddCustomer();*/
    validateCusName(e.key);

});

$("#address").keyup(function (e) {
  /*  enableAddCustomer();*/
  validateCusAddress(e.key);
});

$("#tel").keyup(function (e) {
  /*  enableAddCustomer();*/
    validateCusTel(e.key);

});




function addCustomer() {
    let saveCustomer = confirm("Do you want to save this customer?");
    if (saveCustomer.valueOf()) {
        var data = $("#customerForm").serialize();
        console.log(data);
        $.ajax({
            url: "http://localhost:8080/BackEnd/customer",
            method: "POST",
            data: data,
            success: function (res) {
                console.log(res);
                if (res.status == 200) {
                    console.log(res)
                    alert(res.message);

                    loadAllCustomers();

                    clearCustomer();
                   /* generateCustomerId();
                    loadAllCustomerIds();*/
                } else {
                    alert(res.data);
                }

            },
            error: function (ob, textStatus, error) {
                console.log(ob);
                console.log(textStatus);
                console.log(error);
                console.log()
            }
        });


    }


}

function updateCustomer() {
    let updateCustomer = confirm("Do you want to update this customer?");

    if (updateCustomer.valueOf()) {
        var customer={
            id: $("#cusId").val(),
            name: $("#name").val(),
            address: $("#address").val(),
            contact: $("#tel").val()
        }

        $.ajax({
            url: "http://localhost:8080/BackEnd/customer",
            method: "PUT",
            data: JSON.stringify(customer),
            success: function (res) {
                console.log(res);
                if (res.status == 200) {
                    console.log(res)
                    alert(res.message);

                    loadAllCustomers();
                    clearCustomer();
                    /*generateCustomerId();*/
                } else {
                    alert(res.data);
                }

            },
            error: function (ob, textStatus, error) {
                console.log(ob);
                console.log(textStatus);
                console.log(error);
                console.log()
            }
        });



    }
}

function findCustomer() {
    let cusId = $("#customerSearchID").val();
    console.log(cusId)
    $.ajax({
        url: "http://localhost:8080/BackEnd/customer?option=SEARCH&cusId="+ cusId,
        method: "GET",
        success: function (resp) {
            $("#cusId").val(resp.id);
            $("#name").val(resp.name);
            $("#address").val(resp.address);
            $("#tel").val(resp.contact_No);

        },
        error: function (ob, statusText, error) {
            alert(statusText);
        }
    });


}

function deleteCustomer() {
    let deleteCustomer = confirm("Do you want to delete this customer?");
    if (deleteCustomer.valueOf()) {
        var cusId=$("#cusId").val();
        $.ajax({
            url: "http://localhost:8080/BackEnd/customer?cusId=" + cusId,
            method: "DELETE",
            success: function (resp) {

                    alert($("#cusId").val() + " " + "Customer Successfully Deleted.");
                    loadAllCustomers();
                    clearCustomer();
                  /*  generateCustomerId();*/
                    /*loadAllCustomerIds();*/

            },
            error: function (ob, statusText, error) {
                alert(statusText);
               /* loadAllCustomer();*/
            }
        });


    }
}

function generateCustomerId() {

    $.ajax({
        url: "http://localhost:8080/BackEnd/customer?option=ID",
        method: "GET",
        success: function (resp) {
            console.log(resp);
            $("#cusId").val(resp.id);

        },
        error: function (ob, statusText, error) {
            alert(statusText);
        }
    });

}



function loadAllCustomers() {

    $.ajax({
        url: "http://localhost:8080/BackEnd/customer?option=GETALL",
        method: "GET",
        success: function (resp) {

            $("#cusTbl>tr").remove();

            for (let customer of resp.data) {
                let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.contact_No}</td></tr>`;
                $("#cusTbl").append(row)
            }
            $("#cusTbl>tr").off('click');
            $("#cusTbl>tr").off('dblclick');

            clearCustomer();
            bindClickEvents();
           /* generateCustomerIds();*/
        },
        error: function (ob, statusText, error) {
            alert(statusText);
        }
    });






}
/*
function customerNotExist(){
    for (let customer of customers) {
        if (customer.getId()==$("#cusId").val()){
            return false;
        }
    }
    return true;
}*/

function bindClickEvents(){
    $("#cusTbl>tr").click(function () {
        let id = $(this).children(':first-child').html();
        let name = $(this).children(':nth-child(2)').html();
        let address = $(this).children(':nth-child(3)').html();
        let tel = $(this).children(':nth-child(4)').html();

        $("#cusId").val(id);
        $("#name").val(name);
        $("#address").val(address);
        $("#tel").val(tel);
    });

    $("#cusTbl>tr").dblclick(function () {
        let deleteCustomer = confirm("Do you want to delete this customer?");
        if (deleteCustomer.valueOf()) {
            let rowCusId = $(this).children(':first-child').html();
            customers.find(function (e) {
                if (e.getId() == rowCusId) {

                    customers.splice(customers.indexOf(e), 1);
                }
            });

            loadAllCustomers();
            clearCustomer();
            loadAllCustomerIds();
        }
    });
}

function clearCustomer() {
    $("#cusId").val("");
    $("#cusId").css('border-color', 'Silver');
    $("#name").val("");
    $("#name").css('border-color', 'Silver');
    $("#address").val("");
    $("#address").css('border-color', 'Silver');
    $("#tel").val("");
    $("#tel").css('border-color', 'Silver');
    /*enableAddCustomer();*/
}



$("#AddCustomer").click(function () {
    addCustomer();
});

$("#UpdateCustomer").click(function () {
    updateCustomer();
});

$("#DeleteCustomer").click(function () {
    deleteCustomer();
});

$("#searchCustomer").click(function () {
    findCustomer();

});

$("#cancelCustomer").click(function () {
    clearCustomer();
   /* generateCustomerId();*/
});

