function post() {
  $.ajaxSetup({
    contentType: "application/json; charset=utf-8"
  });
  console.log("Fooo");
  var variables = {
    firstname: {
      value: $("#firstname").val(),
      type: "String"
    },
    lastname: {
      value: $("#lastname").val(),
      type: "String"
    },
    email: {
      value: $("#email").val(),
      type: "String"
    },
    Rotaryclub: {
      value: $("#Rotaryclub").val(),
      type: "String"
    },
    YEO: {
      value: $("#YEO").val(),
      type: "String"
    },
    districtID: {
      value: $("#districtID").val(),
      type: "Integer"
    },
    emailsOn: {
      value: "false",
      type: "Boolean"
    }
  };

  console.log(JSON.stringify({ variables }));
  $.post(
    "/engine-rest/process-definition/key/ApplicationForExchange/start",
    JSON.stringify({ variables })
  )
    .done(function(msg) {
      console.log("Post request was successful!");
      window.location.replace("http://stackoverflow.com");
      window.location.href = "./give-thanks.html";
    })
    .fail(function(xhr, status, error) {
      // error handling
      alert("An error occured while trying to register you. Try again later!");
    });
}
