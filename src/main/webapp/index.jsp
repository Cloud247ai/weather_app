<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Weather App</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            background-image: url('https://i.pinimg.com/originals/09/a7/01/09a701c1aa2106a4ca1b9a174f0a4862.gif');
            background-size: cover;
            background-repeat: no-repeat;
            background-attachment: fixed;
            font-family: 'Segoe UI', sans-serif;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
        }
        .form-box {
            background: rgba(255, 255, 255, 0.1);
            border-radius: 20px;
            padding: 40px 30px;
            backdrop-filter: blur(10px);
            box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
            text-align: center;
            transition: transform 0.3s ease;
        }
        .form-box:hover {
            transform: scale(1.02);
        }
        .form-control {
            font-size: 1.1rem;
            padding: 12px;
            border-radius: 10px;
            border: none;
        }
        .btn {
            font-size: 1.1rem;
            padding: 12px 25px;
            border-radius: 10px;
        }
        .btn-success {
            background: linear-gradient(to right, #00c6ff, #0072ff);
            border: none;
            transition: background 0.4s ease;
        }
        .btn-success:hover {
            background: linear-gradient(to right, #0072ff, #00c6ff);
        }
        ::placeholder {
            color: #ccc;
        }
        h2 {
            font-weight: 700;
            font-size: 2rem;
            margin-bottom: 25px;
        }
    </style>
</head>
<body>
    <div class="form-box">
        <h2>🌍 Real-Time Weather Finder</h2>
        <form action="WeatherServlet" method="get">
            <input type="text" name="place" class="form-control mb-3" placeholder="🔎 Type a city, e.g., New York,US" required>
            <button type="submit" class="btn btn-success">Check Weather</button>
        </form>
    </div>
</body>
</html>
