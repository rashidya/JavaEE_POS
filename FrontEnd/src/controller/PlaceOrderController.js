/*generateOrderId();*/

var cartItems=new Array();

$("#cartTbl").css('overflow');
var regDecimal = /^([0-9.]{1,})$/;
var regExOrderId = /^(OD-)[0-9]{3}$/;
var regExCusQty = /^([0-9]{1,})$/;


$("#cusQtyPlaceOrder").keyup(function (e) {
    let cusQty = parseInt($("#cusQtyPlaceOrder").val());
    let qty = parseInt($("#qtyPlaceOrder").val());
    if (regExCusQty.test($("#cusQtyPlaceOrder").val())) {
        $("#errorCustomerQty").text("");
        if (cusQty <= qty) {
            $("#cusQtyPlaceOrder").css('border-color', 'Green');
            $("#errorOverCustomerQty").text("");
            if (e.key == "Enter") {
                addItemToCart();
            }
        } else {
            $("#errorOverCustomerQty").text(`! Please enter an amount lover than ${qty}`);
            $("#cusQtyPlaceOrder").css('border-color', 'Red');
        }

    } else {
        $("#cusQtyPlaceOrder").css('border-color', 'Red');
        $("#errorCustomerQty").text("! Please enter an amount");
    }

});

$("#cashPlaceOrder").keyup(function (e) {
    setBalance();
    enableDisablePlaceOrderBtn();

});

$("#discountPlaceOrder").keyup(function (e) {
    let discount = parseFloat($("#discountPlaceOrder").val());

    if (regDecimal.test($("#discountPlaceOrder").val()) && discount <= 100) {
        $("#discountPlaceOrder").css('border-color', 'silver');
        $("#errorDiscount").css('display', 'none');
        setNetTotal();
        setBalance();
        enableDisablePlaceOrderBtn();
        if (e.key == "Enter") {

        }

    } else {
        $("#discountPlaceOrder").css('border-color', 'Red');
        $("#errorDiscount").css('display', 'block');
    }


});

$("#orderIdPlaceOrder").keyup(function (e) {
  /* if (validateOrderId()) {
       findOrder();
   }*/
});


$("#selectCustomer").on('change', function () {
    let selectedId = $(this).find('option:selected').html();

    $.ajax({
        url: "http://localhost:8080/BackEnd/order?option=Customer&id="+ selectedId,
        method: "GET",
        success: function (resp) {

            $("#cusIdPlaceOrder").val(resp.id);
            $("#cusNamePlaceOrder").val(resp.name);
            $("#cusAddressPlaceOrder").val(resp.address);
            $("#cusTelPlaceOrder").val(resp.contact_No);

        },
        error: function (ob, statusText, error) {
            alert("There is no customer with this ID");
        }
    });


});

$("#selectItem").on('change', function () {
    let selectedId = $(this).find('option:selected').html();

    $.ajax({
        url: "http://localhost:8080/BackEnd/order?option=Item&id="+ selectedId,
        method: "GET",
        success: function (resp) {
            $("#itemId").val(resp.itemId);
            $("#itemName").val(resp.itemName);
            $("#price").val(resp.price);
            $("#Qty").val(resp.qty);

            $("#itemIdPlaceOrder").val(resp.itemId);
            $("#itemNamePlaceOrder").val(resp.itemName);
            $("#pricePlaceOrder").val(resp.price);
            $("#qtyPlaceOrder").val(resp.qty);

        },
        error: function (ob, statusText, error) {
            alert("There is no item with this ID");
        }
    });




});



function placeOrder() {


        var order = {
            orderId:$("#orderIdPlaceOrder").val(),
            orderDate:$("#orderDatePlaceOrder").val(),
            cusId:$("#cusIdPlaceOrder").val(),
            total:$("#netTotalPlaceOrder").text(),
            orderItems:cartItems
        }


        $.ajax({
            url: "http://localhost:8080/BackEnd/order",
            method: "POST",
            data: JSON.stringify(order),
            success: function (res) {
                console.log(res);
                if (res.status == 200) {
                    console.log(res)
                    alert(res.message);

                    loadAllItems();
                    clearAll();

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

/*function findOrder(){
    orders.find(function (o) {
        if (o.getId() === $("#orderIdPlaceOrder").val()) {
            $("#orderDatePlaceOrder").val(o.getDate());
            $("#netTotalPlaceOrder").val(o.getTotal());
            $("#grossTotalPlaceOrder").val(o.getTotal());
            customers.find(function (c) {
                if (c.getId() === o.getCusId()) {
                    $("#cusIdPlaceOrder").val(c.getId());
                    $("#cusNamePlaceOrder").val(c.getName());
                    $("#cusAddressPlaceOrder").val(c.getAddress());
                    $("#cusTelPlaceOrder").val(c.getTel());
                }
            });

            cartItems = o.getOrderItems();
            loadCartTable();
        }
    });
}*/

function validateAllPlaceOrder(){
    let today = new Date().getDate();

    if (validateOrderId()) {
        if ($("#cusIdPlaceOrder").val()!==''){
            if (cartItems.length!==0){
                if (validateCash()) {
                    return true;
                } else {
                    return false;
                }
            }else {
                return false;
            }
        }else{
            return false;
        }
    } else {
        return false;
    }
}

function enableDisablePlaceOrderBtn() {
    console.log("enable place order")
    if (validateAllPlaceOrder()){
        $("#btnPlaceOrder").attr('disabled', false);
    }else {
        $("#btnPlaceOrder").attr('disabled', true);
    }
}

function validateOrderId(){
    if (regExOrderId.test($("#orderIdPlaceOrder").val())){
        $("#orderIdPlaceOrder").css('border-color', 'Green');
        $("#errorOrderId").css('display', 'none');
        return true;
        /*if (isOrderIdExist()){
            return false;
        }else {
            return true;
        }*/

    }else {
        $("#orderIdPlaceOrder").css('border-color', 'Red');
        $("#errorOrderId").css('display', 'block');
        return false;
    }
}

function validateCash(){
    let cash = parseFloat($("#cashPlaceOrder").val());
    let netTotal = parseFloat($("#netTotalPlaceOrder").text());
    if (regDecimal.test($("#cashPlaceOrder").val()) && netTotal <= cash){
        setBalance();
        $("#cashPlaceOrder").css('border-color', 'Silver');
        return true;
    }else {
        $("#cashPlaceOrder").css('border-color', 'Red');
        $("#balancePlaceOrder").val("");
        return false;
    }
}

/*
function isOrderIdExist(){
    orders.find(function (e){
        if (e.getId()===$("#orderIdPlaceOrder").val() ){
            return true;
        }
    });
    return false;
}*/

/*
function generateOrderId() {
    var tempId;
    if (orders.length !== 0) {

        var id = orders[orders.length - 1].getId();
        var temp = id.split("-")[1];
        temp++;
        tempId = (temp < 10) ? "OD-00" + temp : (temp < 100) ? "OD-0" + temp : "OD-" + temp;

    } else {
        tempId = "OD-001";
    }

    $("#orderIdPlaceOrder").val(tempId);
}*/


function loadAllItemIds() {
    $("#selectItem>option").remove();
    let i = 1;

    for (let item of itemsArray) {

        let option = `<option value="i">${item.itemId}</option>`;
        $("#selectItem").append(option);
        i++;
    }

}

function loadAllCustomerIds() {
    $("#selectCustomer>option").remove();

    let i = 1;
    for (let customer of customersArray) {
        let option = `<option value="i">${customer.id}</option>`;
        $("#selectCustomer").append(option);
        i++;
    }


}



function addItemToCart() {

    let orderId = $("#orderIdPlaceOrder").val();
    let itemCode = $("#itemIdPlaceOrder").val();
    let itemName = $("#itemNamePlaceOrder").val();
    let price = $("#pricePlaceOrder").val();
    let cusQty = $("#cusQtyPlaceOrder").val();
    let total = (cusQty) * (price);

    for (let i = 0; i < cartItems.length; i++) {
        if (cartItems[i].getId() === itemCode) {
            let newQty
            if (!isNaN(itemCode) && ("#addCart").text() === "Add") {
                newQty = parseInt(cartItems[i].getCusQty()) + parseInt(cusQty);
            } else {
                newQty = cusQty;
            }

            cartItems[i].setQty(newQty);
            cartItems[i].setTotal(newQty * price);
            loadCartTable();
            clearItemFields();
            setGrossTotal();
            return;

        }

    }


    cartItems.push(new CartItem(itemCode,itemName,price,cusQty,total));


    loadCartTable();
    clearItemFields();
    setGrossTotal();

}

/*function setDate() {
    $('#orderDatePlaceOrder').datepicker().datepicker('setDate', 'today');
}*/

function setGrossTotal() {
    let grossTotal = 0;
    for (let cartItem of cartItems) {
        grossTotal = parseFloat(grossTotal) + parseFloat(cartItem.getTotal());
    }
    $("#grossTotalPlaceOrder").text(grossTotal);
    setNetTotal();
}

function setNetTotal() {
    let discount = parseFloat($("#discountPlaceOrder").val());
    let grossTotal = parseFloat($("#grossTotalPlaceOrder").text());
    let netTotal = grossTotal;
    if (!isNaN(discount)) {
        netTotal = grossTotal - ((grossTotal * discount) / 100.0);
    }

    $("#netTotalPlaceOrder").text(netTotal);
}

function setBalance() {
    let cash = parseFloat($("#cashPlaceOrder").val());
    let netTotal = parseFloat($("#netTotalPlaceOrder").text());

    if (!isNaN(cash)) {
        let balance = cash - netTotal;
        $("#balancePlaceOrder").text(balance);
    }

}

function loadCartTable() {

    $("#cartTbl>tr").remove();
    for (let cartItem of cartItems) {
        let total = parseFloat(cartItem.getTotal());
        let row = `<tr><td>${cartItem.getId()}</td><td>${cartItem.getName()}</td><td>${cartItem.getPrice()}</td><td>${cartItem.getQty()}</td><td>${total}</td></tr>`;
        $("#cartTbl").append(row);
    }


    $("#cartTbl>tr").off('click');

    $("#cartTbl>tr").click(function () {
        let id = $(this).children(':first-child').html();
        let itemName = $(this).children(':nth-child(2)').html();
        let price = $(this).children(':nth-child(3)').html();
        let qty = $(this).children(':nth-child(4)').html();

        $("#itemIdPlaceOrder").val(id);
        $("#itemNamePlaceOrder").val(itemName);
        $("#pricePlaceOrder").val(price);
        $("#cusQtyPlaceOrder").val(qty);

        items.find(function (e) {
            if (e.getId() === id) {
                $("#qtyPlaceOrder").val(e.getQty());
            }
        });

        $("#addCart").text("Update");
    });
}

function clearItemFields() {
    $("#itemIdPlaceOrder").val("");
    $("#itemNamePlaceOrder").val("");
    $("#pricePlaceOrder").val("");
    $("#cusQtyPlaceOrder").val("");
    $("#qtyPlaceOrder").val("");
    $("#cusQtyPlaceOrder").css('border-color', 'Silver');
    $("#addCart").text("Add");
}

function clearInvoiceFields() {
    $("#orderIdPlaceOrder").val("");
    // $("#orderDatePlaceOrder").val("");
    $("#cusIdPlaceOrder").val("");
    $("#cusNamePlaceOrder").val("");
    $("#cusAddressPlaceOrder").val("");
    $("#cusTelPlaceOrder").val("");
}

function clearBillDetails() {
    $("#grossTotalPlaceOrder").text("0.00");
    $("#netTotalPlaceOrder").text("0.00");
    $("#discountPlaceOrder").val("");
    $("#cashPlaceOrder").val("");
    $("#balancePlaceOrder").text("0.00");
    $("#discountPlaceOrder").css('border-color', 'Silver');
    $("#cashPlaceOrder").css('border-color', 'Silver');
}

function clearAll() {
    clearInvoiceFields();
    clearItemFields();
    clearBillDetails();
    $("#cartTbl>tr").remove();
    cartItems = [];
  /*  generateOrderId();*/
}


$("#addCart").click(function () {
    addItemToCart();
});

$("#btnPlaceOrder").click(function () {
    placeOrder();


});

$("#btnCancelPlaceOrder").click(function () {
    clearAll();
});



function CartItem(id, name, price, qty, total) {
    this.itemId = id;
    this.itemName = name;
    this.itemPrice = price;
    this.itemQty = qty;
    this.total = total;

    this.getId = function () {
        return this.itemId;
    }
    this.setId = function (_id) {
        this.itemId = _id;
    }

    this.getName = function () {
        return this.itemName;
    }

    this.setName = function (_name) {
        this.itemName = _name;
    }
    this.getPrice = function () {
        return this.itemPrice;
    }

    this.setPrice = function (_price) {
        this.itemPrice = _price;
    }
    this.getQty = function () {
        return this.itemQty;
    }

    this.setQty= function (_qty) {
        this.itemQty = _qty;
    }

    this.getTotal = function () {
        return this.total;
    }

    this.setTotal= function (_total) {
        this.total = _total;
    }

}