  $( document ).ready(function() {
    $('.js_enabled').css('display', 'block');
  });

  function ajaxRequest(options) {
    $.ajaxSetup({
      beforeSend: function(xhr) {
        xhr.setRequestHeader('x-sent-with', 'ajax');
      }
    });

    $.ajax(options);
  }

  function ajaxGetRequest(url, successFunc, errorFunc, extraOptions) {
    if (!extraOptions) {
      extraOptions = {};
    }

    if (!errorFunc) {
      errorFunc = function(result) {
          window.location.href = '/';
      };
    }
    var options = {
      'url': url,
      'type': 'GET',
      cache: false,
      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
      xhr: function () {
        var xhr = jQuery.ajaxSettings.xhr();
        var setRequestHeader = xhr.setRequestHeader;
        xhr.setRequestHeader = function (name, value) {
        if (name == 'X-Requested-With') return;
          setRequestHeader.call(this, name, value);
        };
        return xhr;
      },
      success: successFunc,
      error: errorFunc
    };

    if (Object.keys(extraOptions).length) {
      $.extend(true, options, extraOptions);
    }

    ajaxRequest(options);
  }

  function ajaxPostRequest(url, data, successFunc, errorFunc) {
    if (!errorFunc) {
      errorFunc = function(result) {
        window.location.href = '/';
      };
    }
    var options = {
      'url': url,
      'type': 'POST',
      data: data,
      cache: false,
      contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
      xhr: function () {
        var xhr = jQuery.ajaxSettings.xhr();
        var setRequestHeader = xhr.setRequestHeader;
        xhr.setRequestHeader = function (name, value) {
          if (name == 'X-Requested-With') return;
          setRequestHeader.call(this, name, value);
        };
        return xhr;
      },
      success: successFunc,
      error: errorFunc
    };

    ajaxRequest(options);
  }

  /*function getAssets() {

    var url = '/search';

    var successFunction = function(result) {

        $('html').html(result);
    };

    var errorFunction = function(result) {
        $('html').html(result.responseText);
    };

    var data = $('form').serialize();

    ajaxPostRequest(url, data, successFunction, errorFunction);
  }*/

  function getAssetInformation(link) {
    var assetId = $(link).attr('data-nasa_id');

    var url = "/asset/" + assetId;

    var successFunction = function(result) {
        $('.results_container').html(result);
    };

    var errorFunction = function(result) {
        $('.results_container').html($('.error-condition'));
        $('.error_condition').css('display', 'block');
    };

    ajaxGetRequest(url, successFunction, errorFunction);
  }
