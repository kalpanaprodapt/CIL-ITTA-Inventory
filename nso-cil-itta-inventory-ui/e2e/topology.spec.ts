import { expect, test } from '@playwright/test';

test.describe('Topology and Links', () => {
  test('Link list renders', async ({ page }) => {
    await page.goto('/topology/links');
    await expect(page.getByRole('heading', { name: 'Links' }).or(page.getByRole('heading', { name: 'Linkes' }))).toBeVisible();
  });

  test('Link create form is reachable', async ({ page }) => {
    await page.goto('/topology/links/new');
    await expect(page.getByRole('form', { name: 'Link form' })).toBeVisible();
  });

  test('Topology Node list renders', async ({ page }) => {
    await page.goto('/topology/topology-nodes');
    await expect(page.getByRole('heading', { name: 'Topology Nodes' }).or(page.getByRole('heading', { name: 'Topology Nodees' }))).toBeVisible();
  });

  test('Topology Node create form is reachable', async ({ page }) => {
    await page.goto('/topology/topology-nodes/new');
    await expect(page.getByRole('form', { name: 'Topology Node form' })).toBeVisible();
  });

  test('Topology Edge list renders', async ({ page }) => {
    await page.goto('/topology/topology-edges');
    await expect(page.getByRole('heading', { name: 'Topology Edges' }).or(page.getByRole('heading', { name: 'Topology Edgees' }))).toBeVisible();
  });

  test('Topology Edge create form is reachable', async ({ page }) => {
    await page.goto('/topology/topology-edges/new');
    await expect(page.getByRole('form', { name: 'Topology Edge form' })).toBeVisible();
  });

  test('Topology View list renders', async ({ page }) => {
    await page.goto('/topology/topology-views');
    await expect(page.getByRole('heading', { name: 'Topology Views' }).or(page.getByRole('heading', { name: 'Topology Viewes' }))).toBeVisible();
  });

  test('Topology View create form is reachable', async ({ page }) => {
    await page.goto('/topology/topology-views/new');
    await expect(page.getByRole('form', { name: 'Topology View form' })).toBeVisible();
  });

  test('Path Trace list renders', async ({ page }) => {
    await page.goto('/topology/path-traces');
    await expect(page.getByRole('heading', { name: 'Path Traces' }).or(page.getByRole('heading', { name: 'Path Tracees' }))).toBeVisible();
  });

  test('Path Trace create form is reachable', async ({ page }) => {
    await page.goto('/topology/path-traces/new');
    await expect(page.getByRole('form', { name: 'Path Trace form' })).toBeVisible();
  });
});
