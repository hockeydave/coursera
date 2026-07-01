# Function to calculate fibonacci number
def fibonacci(n):
    if n <= 0:
        return 0
    elif n == 1:
        return 1
    else:
        return fibonacci(n-1) + fibonacci(n-2)
    
# Function to check if a string is a palindrome
def is_palindrome(s):
    return s == s[::-1]
