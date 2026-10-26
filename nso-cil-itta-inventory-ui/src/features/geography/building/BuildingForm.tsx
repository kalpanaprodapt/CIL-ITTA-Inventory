import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { BUILDING_OWNERSHIP_OPTIONS, BUILDING_LIFECYCLE_STAGE_OPTIONS, BUILDING_CRITICALITY_OPTIONS, BuildingOwnership, BuildingLifecycleStage, BuildingCriticality } from './types';
import { validateBuilding, cleanBuildingValues } from './schema';
import { BUILDING_DEFAULTS } from './types';
import type { BuildingFormValues, BuildingInput } from './types';

interface Props {
  initial?: BuildingFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: BuildingInput) => void;
  onCancel?: () => void;
}

export function BuildingForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<BuildingFormValues>({ ...BUILDING_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof BuildingFormValues>(key: K, value: BuildingFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateBuilding(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanBuildingValues(values as Record<string, unknown>) as unknown as BuildingInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Building form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Site Id" htmlFor="siteId" error={errors.siteId} required={true}>
        <Input
          id="siteId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.siteId ?? ''}
          onChange={(v) => set('siteId', v)}
        />
      </FormField>
      <FormField label="Floors" htmlFor="floors" error={errors.floors} required={false}>
        <NumberInput
          id="floors"
          value={values.floors}
          step={1}
          onChange={(v) => set('floors', v)}
        />
      </FormField>
      <FormField label="Year Built" htmlFor="yearBuilt" error={errors.yearBuilt} required={false}>
        <NumberInput
          id="yearBuilt"
          value={values.yearBuilt}
          step={1}
          onChange={(v) => set('yearBuilt', v)}
        />
      </FormField>
      <FormField label="Ownership" htmlFor="ownership" error={errors.ownership} required={false}>
        <Select
          id="ownership"
          value={values.ownership ?? ''}
          options={BUILDING_OWNERSHIP_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('ownership', (v || undefined) as BuildingOwnership | undefined)}
        />
      </FormField>
      <FormField label="Lease Expiry" htmlFor="leaseExpiry" error={errors.leaseExpiry} required={false}>
        <DateInput
          id="leaseExpiry"
          value={values.leaseExpiry ?? ''}
          onChange={(v) => set('leaseExpiry', v)}
        />
      </FormField>
      <FormField label="Security Level" htmlFor="securityLevel" error={errors.securityLevel} required={false}>
        <NumberInput
          id="securityLevel"
          value={values.securityLevel}
          step={1}
          onChange={(v) => set('securityLevel', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={BUILDING_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as BuildingLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={BUILDING_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as BuildingCriticality | undefined)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}
