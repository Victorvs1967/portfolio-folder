export class User {
  fullname: string;
  username: string;
  password: string;
  email: string;
  roles: string[];

  constructor(fullname: string, username: string, email: string, password: string) {
    this.fullname = fullname;
    this.username = username;
    this.password = password;
    this.email = email;
    this.roles = ['user'];
  }
}