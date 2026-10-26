import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { FAN_TRAY_AIRFLOW_OPTIONS, FAN_TRAY_LIFECYCLE_STAGE_OPTIONS, FanTrayAirflow, FanTrayLifecycleStage } from './types';
import { validateFanTray, cleanFanTrayValues } from './schema';
import { FANTRAY_DEFAULTS } from './types';
import type { FanTrayFormValues, FanTrayInput } from './types';

interface Props {
  initial?: FanTrayFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: FanTrayInput) => void;
  onCancel?: () => void;
}

export function FanTrayForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<FanTrayFormValues>({ ...FANTRAY_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof FanTrayFormValues>(key: K, value: FanTrayFormValues[K]) {
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
    const result = validateFanTray(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanFanTrayValues(values as Record<string, unknown>) as unknown as FanTrayInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Fan Tray form">
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
      <FormField label="Chassis Id" htmlFor="chassisId" error={errors.chassisId} required={true}>
        <Input
          id="chassisId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.chassisId ?? ''}
          onChange={(v) => set('chassisId', v)}
        />
      </FormField>
      <FormField label="Slot Number" htmlFor="slotNumber" error={errors.slotNumber} required={false}>
        <NumberInput
          id="slotNumber"
          value={values.slotNumber}
          step={1}
          onChange={(v) => set('slotNumber', v)}
        />
      </FormField>
      <FormField label="Fan Count" htmlFor="fanCount" error={errors.fanCount} required={false}>
        <NumberInput
          id="fanCount"
          value={values.fanCount}
          step={1}
          onChange={(v) => set('fanCount', v)}
        />
      </FormField>
      <FormField label="Airflow" htmlFor="airflow" error={errors.airflow} required={false}>
        <Select
          id="airflow"
          value={values.airflow ?? ''}
          options={FAN_TRAY_AIRFLOW_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('airflow', (v || undefined) as FanTrayAirflow | undefined)}
        />
      </FormField>
      <FormField label="Serial Number" htmlFor="serialNumber" error={errors.serialNumber} required={false}>
        <Input
          id="serialNumber"
          value={values.serialNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('serialNumber', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
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
          options={FAN_TRAY_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as FanTrayLifecycleStage | undefined)}
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
