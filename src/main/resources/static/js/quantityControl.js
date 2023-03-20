$(document).ready(function(){
    $(".linkMinus").on("click" , function(e) {
       e.preventDefault();
       productId = $(this).attr("pid");
       quantity = $("#quantity" + productId);
       newQuantity = parseInt(quantity.val()) - 1;
       if(newQuantity > 0){
           quantity.val(newQuantity);
       }
    });
    $(".linkPlus").on("click" , function(e) {
       e.preventDefault();
       productId = $(this).attr("pid");
       quantity = $("#quantity" + productId);
       newQuantity = parseInt(quantity.val()) + 1;
       quantity.val(newQuantity);
    });
});
