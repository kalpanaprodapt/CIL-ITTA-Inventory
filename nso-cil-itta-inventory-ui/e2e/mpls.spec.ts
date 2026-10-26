import { expect, test } from '@playwright/test';

test.describe('MPLS and VPN', () => {
  test('Mpls Lsp list renders', async ({ page }) => {
    await page.goto('/mpls/mpls-lsps');
    await expect(page.getByRole('heading', { name: 'Mpls Lsps' }).or(page.getByRole('heading', { name: 'Mpls Lspes' }))).toBeVisible();
  });

  test('Mpls Lsp create form is reachable', async ({ page }) => {
    await page.goto('/mpls/mpls-lsps/new');
    await expect(page.getByRole('form', { name: 'Mpls Lsp form' })).toBeVisible();
  });

  test('Vpn Service list renders', async ({ page }) => {
    await page.goto('/mpls/vpn-services');
    await expect(page.getByRole('heading', { name: 'Vpn Services' }).or(page.getByRole('heading', { name: 'Vpn Servicees' }))).toBeVisible();
  });

  test('Vpn Service create form is reachable', async ({ page }) => {
    await page.goto('/mpls/vpn-services/new');
    await expect(page.getByRole('form', { name: 'Vpn Service form' })).toBeVisible();
  });

  test('Pseudo Wire list renders', async ({ page }) => {
    await page.goto('/mpls/pseudo-wires');
    await expect(page.getByRole('heading', { name: 'Pseudo Wires' }).or(page.getByRole('heading', { name: 'Pseudo Wirees' }))).toBeVisible();
  });

  test('Pseudo Wire create form is reachable', async ({ page }) => {
    await page.goto('/mpls/pseudo-wires/new');
    await expect(page.getByRole('form', { name: 'Pseudo Wire form' })).toBeVisible();
  });

  test('Tunnel Endpoint list renders', async ({ page }) => {
    await page.goto('/mpls/tunnel-endpoints');
    await expect(page.getByRole('heading', { name: 'Tunnel Endpoints' }).or(page.getByRole('heading', { name: 'Tunnel Endpointes' }))).toBeVisible();
  });

  test('Tunnel Endpoint create form is reachable', async ({ page }) => {
    await page.goto('/mpls/tunnel-endpoints/new');
    await expect(page.getByRole('form', { name: 'Tunnel Endpoint form' })).toBeVisible();
  });

  test('Qos Policy list renders', async ({ page }) => {
    await page.goto('/mpls/qos-policies');
    await expect(page.getByRole('heading', { name: 'Qos Policys' }).or(page.getByRole('heading', { name: 'Qos Policyes' }))).toBeVisible();
  });

  test('Qos Policy create form is reachable', async ({ page }) => {
    await page.goto('/mpls/qos-policies/new');
    await expect(page.getByRole('form', { name: 'Qos Policy form' })).toBeVisible();
  });
});
