export class TokenResponse {

  token: string;
  type: string;
  username: string;
  role: string;

  statusCode: string;

  constructor(token: string, type: string, username: string) {
    this.token = token;
    this.type = type;
    this.username = username;
    this.role = 'user';
    this.statusCode = '200';
  }

}