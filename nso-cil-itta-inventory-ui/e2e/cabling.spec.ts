import { expect, test } from '@playwright/test';

test.describe('Cabling', () => {
  test('Cable list renders', async ({ page }) => {
    await page.goto('/cabling/cables');
    await expect(page.getByRole('heading', { name: 'Cables' }).or(page.getByRole('heading', { name: 'Cablees' }))).toBeVisible();
  });

  test('Cable create form is reachable', async ({ page }) => {
    await page.goto('/cabling/cables/new');
    await expect(page.getByRole('form', { name: 'Cable form' })).toBeVisible();
  });

  test('Patch Panel list renders', async ({ page }) => {
    await page.goto('/cabling/patch-panels');
    await expect(page.getByRole('heading', { name: 'Patch Panels' }).or(page.getByRole('heading', { name: 'Patch Paneles' }))).toBeVisible();
  });

  test('Patch Panel create form is reachable', async ({ page }) => {
    await page.goto('/cabling/patch-panels/new');
    await expect(page.getByRole('form', { name: 'Patch Panel form' })).toBeVisible();
  });

  test('Patch Port list renders', async ({ page }) => {
    await page.goto('/cabling/patch-ports');
    await expect(page.getByRole('heading', { name: 'Patch Ports' }).or(page.getByRole('heading', { name: 'Patch Portes' }))).toBeVisible();
  });

  test('Patch Port create form is reachable', async ({ page }) => {
    await page.goto('/cabling/patch-ports/new');
    await expect(page.getByRole('form', { name: 'Patch Port form' })).toBeVisible();
  });

  test('Splice list renders', async ({ page }) => {
    await page.goto('/cabling/splices');
    await expect(page.getByRole('heading', { name: 'Splices' }).or(page.getByRole('heading', { name: 'Splicees' }))).toBeVisible();
  });

  test('Splice create form is reachable', async ({ page }) => {
    await page.goto('/cabling/splices/new');
    await expect(page.getByRole('form', { name: 'Splice form' })).toBeVisible();
  });

  test('Fiber Strand list renders', async ({ page }) => {
    await page.goto('/cabling/fiber-strands');
    await expect(page.getByRole('heading', { name: 'Fiber Strands' }).or(page.getByRole('heading', { name: 'Fiber Strandes' }))).toBeVisible();
  });

  test('Fiber Strand create form is reachable', async ({ page }) => {
    await page.goto('/cabling/fiber-strands/new');
    await expect(page.getByRole('form', { name: 'Fiber Strand form' })).toBeVisible();
  });
});
