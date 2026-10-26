import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { MAINTENANCE_WINDOW_IMPACT_LEVEL_OPTIONS, MAINTENANCE_WINDOW_LIFECYCLE_STAGE_OPTIONS, MaintenanceWindowImpactLevel, MaintenanceWindowLifecycleStage } from './types';
import { validateMaintenanceWindow, cleanMaintenanceWindowValues } from './schema';
import { MAINTENANCEWINDOW_DEFAULTS } from './types';
import type { MaintenanceWindowFormValues, MaintenanceWindowInput } from './types';

interface Props {
  initial?: MaintenanceWindowFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: MaintenanceWindowInput) => void;
  onCancel?: () => void;
}

export function MaintenanceWindowForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<MaintenanceWindowFormValues>({ ...MAINTENANCEWINDOW_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof MaintenanceWindowFormValues>(key: K, value: MaintenanceWindowFormValues[K]) {
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
    const result = validateMaintenanceWindow(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanMaintenanceWindowValues(values as Record<string, unknown>) as unknown as MaintenanceWindowInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Maintenance Window form">
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
      <FormField label="Window Name" htmlFor="windowName" error={errors.windowName} required={true}>
        <Input
          id="windowName"
          value={values.windowName ?? ''}
          maxLength={255}
          onChange={(v) => set('windowName', v)}
        />
      </FormField>
      <FormField label="Starts At" htmlFor="startsAt" error={errors.startsAt} required={true}>
        <DateTimeInput
          id="startsAt"
          value={values.startsAt ?? ''}
          onChange={(v) => set('startsAt', v)}
        />
      </FormField>
      <FormField label="Ends At" htmlFor="endsAt" error={errors.endsAt} required={true}>
        <DateTimeInput
          id="endsAt"
          value={values.endsAt ?? ''}
          onChange={(v) => set('endsAt', v)}
        />
      </FormField>
      <FormField label="Scope" htmlFor="scope" error={errors.scope} required={false}>
        <Input
          id="scope"
          value={values.scope ?? ''}
          maxLength={255}
          onChange={(v) => set('scope', v)}
        />
      </FormField>
      <FormField label="Impact Level" htmlFor="impactLevel" error={errors.impactLevel} required={false}>
        <Select
          id="impactLevel"
          value={values.impactLevel ?? ''}
          options={MAINTENANCE_WINDOW_IMPACT_LEVEL_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('impactLevel', (v || undefined) as MaintenanceWindowImpactLevel | undefined)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={MAINTENANCE_WINDOW_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as MaintenanceWindowLifecycleStage | undefined)}
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
