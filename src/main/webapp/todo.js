$(document).ready(
    showAll()
);

function showAll() {
    $.ajax({
        type: 'GET',
        crossdomain: true,
        url: 'http://localhost:8080/todo/index',
        dataType: 'json'
    }).done(function (data) {
        showData(data);
        showCategories();
    }).fail(function (err) {
        console.log(err);
    });
}

function showData(data) {
    $("#username").text(data[0].user.username);
    $("#table tbody").empty();
    console.log(data);
    for (var task of data) {
        const id = task.id;
        const description = task.description;
        const time = task.created.date.day
            + "-" + task.created.date.month
            + "-" + task.created.date.year;
        const category = task.category.name;
        const done = task.done;
        $('#table tbody').append(
            '<tr><td>' + description + '</td>'
            + '<td align="center">' + time + '</td>'
            + '<td align="center">' + category + '</td>'
            + '<td align="center"><div>'
            + '<input type="checkbox" id="' + id + '" name="done">'
            + '</div></td>'
            + '</tr>');
        $("#" + id).attr("checked", done);
    }
}

function showCategories() {
    $.ajax({
        type: 'GET',
        crossdomain: true,
        url: 'http://localhost:8080/todo/categories',
        dataType: 'json'
    }).done(function (data) {
        console.log(data);
        $("#dp").empty();
        for (var category of data) {
            const id = category.id;
            const name = category.name;
            $('#dp').append(
                '<a class="dropdown-item" href="#" data-id="' + id + '">' + name + '</a>');
        }
    }).fail(function (err) {
        console.log(err);
    });
}

$('#dp').on("click", function(){
    const id = $(':focus').attr('data-id');
    const name = $(':focus').text();
    console.log(id);
    console.log(name);
    $("#dropdownMenuButton").attr('data-id', id);
    $("#dropdownMenuButton").text(name);
});

function addTask() {
    $.ajax({
        type: 'POST',
        crossdomain: true,
        url: 'http://localhost:8080/todo/index',
        data: JSON.stringify({
            description: $("#description").val(),
            categoryId: $("#dropdownMenuButton").attr('data-id'),
            categoryName: $("#dropdownMenuButton").text()
        }),
        dataType: 'json'
    }).done(function (data) {
        showData(data);
    }).fail(function (err) {
        console.log(err);
    });
}

function filterItems() {
    let check = $("#filter").prop("checked");
    if (check) {
        showFilterItems();
    } else {
        showAll();
    }
}

function showFilterItems() {
    $.ajax({
        type: 'POST',
        crossdomain: true,
        url: 'http://localhost:8080/todo/filter',
        dataType: 'json'
    }).done(function (data) {
        showData(data);
    }).fail(function (err) {
        console.log(err);
    });
}

$(document).on('change', ':checkbox', function () {
    let id = $(this).attr("id");
    console.log(id);
    if (id !== "filter") {
        $.ajax({
            type: "POST",
            crossdomain: true,
            url: 'http://localhost:8080/todo/update',
            dataType: 'json',
            data: {id: $(this).attr("id"), done: $(this).prop("checked")},
        }).done(function (data) {
            console.log(data);
            showAll();
        }).fail(function (err) {
            console.log(err);
        });
    }
})

function validate(data) {
    let result = true;
    let answer = '';
    let elements = [$("#description")];
    for (let i = 0; i < elements.length; i++) {
        if (elements[i].val() === '') {
            answer += elements[i].attr("placeholder") + "\n";
            for (let i = 0; i < data.length; i++) {
                if (data[i].val() === '') {
                    answer += data[i].attr("placeholder") + "\n";
                    result = false;
                }
            }
            if (!result) {
                alert(answer);
            }
            return result;
        }
    }
}

function checkFormCreateItem() {
    let elements = [$("#description")];
    return validate(elements);
}

function checkFormRegistration() {
    let elements = [$("#username"), $("#regEmail"), $("#regPassword")];
    return validate(elements);
}

function checkFormAuth() {
    let elements = [$("#email"), $("#password")];
    return validate(elements);
}

function redirectPageLogin() {
    window.location.href = "./auth.do";
}


